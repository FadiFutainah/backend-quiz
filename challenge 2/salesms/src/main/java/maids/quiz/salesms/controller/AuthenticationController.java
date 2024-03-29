package maids.quiz.salesms.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maids.quiz.salesms.dto.ResponseDto;
import maids.quiz.salesms.dto.auth.*;
import maids.quiz.salesms.service.AuthenticationService;
import maids.quiz.salesms.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    final AuthenticationService service;
    final ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<String>> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return service.register(request);
    }

    @PostMapping("/verify")
    public ResponseEntity<ResponseDto<VerifyResponse>> verify(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @RequestBody VerifyRequest requestBody
    ) {
        return service.verify(requestBody, request, response);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponse>> authenticate(
            @RequestBody @Valid LoginRequest request
    ) {
        return service.login(request);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseDto<LoginResponse>> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        return service.refreshToken(request, response);
    }
}
