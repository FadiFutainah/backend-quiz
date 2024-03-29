package maids.quiz.salesms.service;

import maids.quiz.salesms.model.Category;
import maids.quiz.salesms.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends CrudService<Category, Integer> {
    public CategoryService(CategoryRepository categoryRepository) {
        super(categoryRepository);
    }
}
