package maids.quiz.salesms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maids.quiz.salesms.dto.ResponseDto;
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
    public ResponseEntity<ResponseDto<Sale>> add(@Valid @RequestBody Sale sale) {
        return saleService.add(sale);
    }

    @PutMapping
    public ResponseEntity<ResponseDto<Sale>> update(@Valid @RequestBody Sale sale) {
        return saleService.update(sale);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> delete(@PathVariable Integer id) {
        return saleService.delete(id);
    }

}
