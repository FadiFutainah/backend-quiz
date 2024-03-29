package maids.quiz.salesms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maids.quiz.salesms.dto.ResponseDto;
import maids.quiz.salesms.model.Client;
import maids.quiz.salesms.model.Product;
import maids.quiz.salesms.service.ClientService;
import maids.quiz.salesms.dto.auth.ChangePasswordRequest;
import maids.quiz.salesms.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {

    final ClientService clientService;

    @GetMapping
    public ResponseEntity<ResponseDto<Page<Client>>> fetch(Pageable pageable) {
        return clientService.fetch(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Client>> fetch(@PathVariable Integer id) {
        return clientService.fetch(id);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Client>> add(@Valid @RequestBody Client client) {
        return clientService.add(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Client>> update(
            @PathVariable Integer id,
            @Valid @RequestBody Client client
    ) {
        return clientService.update(id, client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> delete(@PathVariable Integer id) {
        return clientService.delete(id);
    }
}
