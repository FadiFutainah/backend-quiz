package maids.quiz.salesms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maids.quiz.salesms.dto.ResponseDto;
import maids.quiz.salesms.dto.client.ClientReportDto;
import maids.quiz.salesms.model.Client;
import maids.quiz.salesms.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

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

    @GetMapping("/report")
    public ResponseEntity<ResponseDto<ClientReportDto>> report(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Instant from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Instant to
    ) {
        return clientService.report(from, to);
    }
}
