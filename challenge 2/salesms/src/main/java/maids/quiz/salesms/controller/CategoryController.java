package maids.quiz.salesms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maids.quiz.salesms.dto.ResponseDto;
import maids.quiz.salesms.model.Category;
import maids.quiz.salesms.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ResponseDto<Page<Category>>> fetch(Pageable pageable) {
        return categoryService.fetch(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<Category>> fetch(@PathVariable Integer id) {
        return categoryService.fetch(id);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Category>> add(@Valid @RequestBody Category category) {
        return categoryService.add(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Category>> update(
            @PathVariable Integer id,
            @Valid @RequestBody Category category
    ) {
        return categoryService.update(id, category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> delete(@PathVariable Integer id) {
        return categoryService.delete(id);
    }

}
