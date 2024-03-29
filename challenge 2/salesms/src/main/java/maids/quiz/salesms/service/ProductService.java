package maids.quiz.salesms.service;

import maids.quiz.salesms.dto.ProductDto;
import maids.quiz.salesms.dto.ResponseDto;
import maids.quiz.salesms.exception.CommonExceptions;
import maids.quiz.salesms.model.Category;
import maids.quiz.salesms.model.Product;
import maids.quiz.salesms.repository.CategoryRepository;
import maids.quiz.salesms.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProductService extends CrudService<Product, Integer> {
    @Autowired
    CategoryRepository categoryRepository;
    ModelMapper modelMapper = new ModelMapper();

    public ProductService(ProductRepository productRepository) {
        super(productRepository);
    }

    private Set<Category> getCategoriesFromIds(Set<Integer> categoryIds) {
        Set<Category> categories = new HashSet<>();
        String message = "Category not found with id: ";
        for (Integer categoryId : categoryIds) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new CommonExceptions.ResourceNotFoundException(message + categoryId));
            categories.add(category);
        }
        return categories;
    }

    public ResponseEntity<ResponseDto<Product>> add(ProductDto resource) {
        Product product = modelMapper.map(resource, Product.class);
        product.setCategories(getCategoriesFromIds(resource.getCategories()));
        return super.add(product);
    }

    public ResponseEntity<ResponseDto<Product>> update(ProductDto resource) {
        Product product = modelMapper.map(resource, Product.class);
        product.setCategories(getCategoriesFromIds(resource.getCategories()));
        return super.update(product);
    }
}
