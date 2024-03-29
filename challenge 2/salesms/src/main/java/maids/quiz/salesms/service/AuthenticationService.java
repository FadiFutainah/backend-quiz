package maids.quiz.salesms.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import maids.quiz.salesms.dto.ResponseDto;
import maids.quiz.salesms.dto.auth.*;
import maids.quiz.salesms.enums.TokenType;
import maids.quiz.salesms.exception.CommonExceptions;
import maids.quiz.salesms.model.Client;
import maids.quiz.salesms.model.Token;
import maids.quiz.salesms.repository.ClientRepository;
import maids.quiz.salesms.repository.TokenRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    final ClientRepository repository;
    final TokenRepository tokenRepository;
    final ClientRepository clientRepository;
    final JwtService jwtService;
    final AuthenticationManager authenticationManager;
    final EmailService emailService;

    private final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    ModelMapper modelMapper = new ModelMapper();

    public ResponseEntity<ResponseDto<String>> register(RegisterRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        var client = modelMapper.map(request, Client.class);
        client.setActivationKey(generateActivationKey());
        var savedClient = repository.save(client);
        emailService.sendVerificationEmail(savedClient.getEmail(), savedClient.getActivationKey());
        return ResponseDto.response("Verification code sent successfully");
    }

    @Transactional
    public ResponseEntity<ResponseDto<VerifyResponse>> verify(
            VerifyRequest requestBody,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String refreshToken = getRefreshTokenFromAuthHeader(request);
        Client client = getClientFromRefreshToken(refreshToken);
        if (!Objects.equals(requestBody.getCode(), client.getActivationKey())) {
            log.info("invalid activation code attempt: session-" + request.getSession()
                    + ", email-" + client.getEmail());
            throw new CommonExceptions.UnauthorizedException("Invalid activation code");
        }
        client.setActivated(true);
        clientRepository.save(client);
        var jwtToken = jwtService.generateToken(client);
        var newRefreshToken = jwtService.generateRefreshToken(client);
        saveClientToken(client, jwtToken);
        var data = VerifyResponse.builder()
                .client(client)
                .accessToken(jwtToken)
                .refreshToken(newRefreshToken)
                .build();
        return ResponseDto.response(data);
    }

    @Transactional
    public ResponseEntity<ResponseDto<LoginResponse>> login(LoginRequest request) {
        var client = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CommonExceptions.ResourceNotFoundException("email does not exist"));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var jwtToken = jwtService.generateToken(client);
        var refreshToken = jwtService.generateRefreshToken(client);
        revokeAllClientTokens(client);
        saveClientToken(client, jwtToken);
        var data = LoginResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
        return ResponseDto.response(data);
    }

    void saveClientToken(Client client, String jwtToken) {
        var token = Token.builder()
                .client(client)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    String generateActivationKey() {
        return (int) Math.floor(Math.random() * (99998 - 10000 + 1) + 10000) + "";
    }

    void revokeAllClientTokens(Client client) {
        log.info("revoked all tokens for client " + client.getEmail());
        var validClientTokens = tokenRepository.findAllValidTokenByClient(client.getId());
        if (validClientTokens.isEmpty())
            return;
        validClientTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validClientTokens);
    }

    Client getClientFromRefreshToken(String refreshToken) {
        String userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            String message = userEmail + "does not exist";
            var client = this.repository.findByEmail(userEmail)
                    .orElseThrow(() -> new CommonExceptions.ResourceNotFoundException(message));
            if (jwtService.isTokenValid(refreshToken, client)) {
                return client;
            }
        }
        throw new CommonExceptions.UnauthorizedException("Invalid token");
    }

    String getRefreshTokenFromAuthHeader(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CommonExceptions.UnauthorizedException("invalid authorization header");
        }
        refreshToken = authHeader.substring(7);
        return refreshToken;
    }

    public ResponseEntity<ResponseDto<LoginResponse>> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String refreshToken = getRefreshTokenFromAuthHeader(request);
        Client client = getClientFromRefreshToken(refreshToken);
        var accessToken = jwtService.generateToken(client);
        revokeAllClientTokens(client);
        saveClientToken(client, accessToken);
        var authResponse = LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        return ResponseDto.response(authResponse);
    }
}
