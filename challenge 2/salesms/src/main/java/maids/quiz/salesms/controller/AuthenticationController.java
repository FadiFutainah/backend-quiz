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
    private final AuthenticationService service;
    private final ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<RegisterResponse>> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return service.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponse>> authenticate(
            @RequestBody @Valid LoginRequest request
    ) {
        return service.login(request);
    }

//    TODO: fix
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

    @PatchMapping()
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        clientService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
}
