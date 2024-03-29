package maids.quiz.salesms.service;

import maids.quiz.salesms.model.Product;
import maids.quiz.salesms.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends CrudService<Product, Integer> {
    public ProductService(ProductRepository productRepository) {
        super(productRepository);
    }
}
