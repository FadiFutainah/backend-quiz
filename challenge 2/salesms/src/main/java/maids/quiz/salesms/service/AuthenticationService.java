package maids.quiz.salesms.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import maids.quiz.salesms.dto.ResponseDto;
import maids.quiz.salesms.dto.auth.LoginRequest;
import maids.quiz.salesms.dto.auth.LoginResponse;
import maids.quiz.salesms.dto.auth.RegisterRequest;
import maids.quiz.salesms.dto.auth.RegisterResponse;
import maids.quiz.salesms.enums.TokenType;
import maids.quiz.salesms.model.Client;
import maids.quiz.salesms.model.Token;
import maids.quiz.salesms.repository.ClientRepository;
import maids.quiz.salesms.repository.TokenRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthenticationService {
    @Autowired
    ClientRepository repository;
    @Autowired
    TokenRepository tokenRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    ModelMapper modelMapper;

    public AuthenticationService() {
        this.modelMapper = new ModelMapper();
//        addPasswordEncodingMapping();
    }

    //    TODO: fix
    void addPasswordEncodingMapping() {
        modelMapper.addMappings(new PropertyMap<RegisterRequest, Client>() {
            @Override
            protected void configure() {
                map().setPassword(passwordEncoder.encode(source.getPassword()));
            }
        });
    }

    public ResponseEntity<ResponseDto<RegisterResponse>> register(RegisterRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        var client = modelMapper.map(request, Client.class);
        var savedClient = repository.save(client);
        var jwtToken = jwtService.generateToken(client);
        var refreshToken = jwtService.generateRefreshToken(client);
        saveClientToken(savedClient, jwtToken);
        var data = RegisterResponse.builder()
                .client(savedClient)
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
        return ResponseDto.response(data);
    }

    public ResponseEntity<ResponseDto<LoginResponse>> login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var client = repository.findByEmail(request.getEmail())
                .orElseThrow();
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

    void revokeAllClientTokens(Client client) {
        var validClientTokens = tokenRepository.findAllValidTokenByClient(client.getId());
        if (validClientTokens.isEmpty())
            return;
        validClientTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validClientTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var client = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, client)) {
                var accessToken = jwtService.generateToken(client);
                revokeAllClientTokens(client);
                saveClientToken(client, accessToken);
                var authResponse = LoginResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
