package maids.quiz.salesms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maids.quiz.salesms.dto.ResponseDto;
import maids.quiz.salesms.model.Product;
import maids.quiz.salesms.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<Product>>> fetch() {
        return productService.fetch();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Product>> fetch(@PathVariable Integer id) {
        return productService.fetch(id);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Product>> add(@Valid @RequestBody Product product) {
        return productService.add(product);
    }

    @PutMapping
    public ResponseEntity<ResponseDto<Product>> update(@Valid @RequestBody Product product) {
        return productService.update(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> delete(@PathVariable Integer id) {
        return productService.delete(id);
    }
}
