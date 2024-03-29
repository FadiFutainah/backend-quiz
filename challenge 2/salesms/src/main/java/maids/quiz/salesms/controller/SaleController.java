package maids.quiz.salesms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maids.quiz.salesms.dto.ResponseDto;
import maids.quiz.salesms.dto.SaleDto;
import maids.quiz.salesms.dto.SaleTransactionDto;
import maids.quiz.salesms.dto.UpdateSaleDto;
import maids.quiz.salesms.model.Sale;
import maids.quiz.salesms.service.SaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale")
@RequiredArgsConstructor
public class SaleController {
    final SaleService saleService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<Sale>>> fetch() {
        return saleService.fetch();
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

}
