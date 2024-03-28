package maids.quiz.salesms.service;

import lombok.RequiredArgsConstructor;
import maids.quiz.salesms.dto.auth.ChangePasswordRequest;
import maids.quiz.salesms.model.Client;
import maids.quiz.salesms.repository.ClientRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final PasswordEncoder passwordEncoder;
    private final ClientRepository repository;
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var client = (Client) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (!passwordEncoder.matches(request.getCurrentPassword(), client.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        client.setPassword(passwordEncoder.encode(request.getNewPassword()));

        repository.save(client);
    }
}