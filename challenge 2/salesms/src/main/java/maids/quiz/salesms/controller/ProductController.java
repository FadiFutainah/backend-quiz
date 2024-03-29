package maids.quiz.salesms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maids.quiz.salesms.dto.product.ProductDto;
import maids.quiz.salesms.dto.ResponseDto;
import maids.quiz.salesms.dto.product.UpdateProductDto;
import maids.quiz.salesms.model.Product;
import maids.quiz.salesms.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    final ProductService productService;

    @GetMapping
    public ResponseEntity<ResponseDto<Page<Product>>> fetch(Pageable pageable) {
        return productService.fetch(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Product>> fetch(@PathVariable Integer id) {
        return productService.fetch(id);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Product>> add(@Valid @RequestBody ProductDto product) {
        return productService.add(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Product>> update(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateProductDto product
    ) {
        return productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> delete(@PathVariable Integer id) {
        return productService.delete(id);
    }
}
