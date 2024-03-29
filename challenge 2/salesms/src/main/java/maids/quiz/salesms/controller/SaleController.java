package maids.quiz.salesms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maids.quiz.salesms.dto.ResponseDto;
import maids.quiz.salesms.dto.SaleDto;
import maids.quiz.salesms.dto.UpdateSaleDto;
import maids.quiz.salesms.model.Sale;
import maids.quiz.salesms.service.SaleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping("/api/sale")
@RequiredArgsConstructor
public class SaleController {
    final SaleService saleService;

    @GetMapping
    public ResponseEntity<ResponseDto<Page<Sale>>> fetch(Pageable pageable) {
        return saleService.fetch(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Sale>> fetch(@PathVariable Integer id) {
        return saleService.fetch(id);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Sale>> add(@Valid @RequestBody SaleDto saleDto) {
        return saleService.createNewSale(saleDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Sale>> update(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateSaleDto updateSaleDto
    ) {
        return saleService.update(id, updateSaleDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> delete(@PathVariable Integer id) {
        return saleService.delete(id);
    }


    @GetMapping("/report")
    public ResponseEntity<ResponseDto<Page<Sale>>> report(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Instant from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Instant to,
            Pageable pageable
    ) {
        return saleService.report(from, to, pageable);
    }
}
