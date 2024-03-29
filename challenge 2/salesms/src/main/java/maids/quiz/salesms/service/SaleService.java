package maids.quiz.salesms.service;

import maids.quiz.salesms.model.Sale;
import maids.quiz.salesms.repository.SaleRepository;
import org.springframework.stereotype.Service;

@Service
public class SaleService extends CrudService<Sale, Integer> {
    public SaleService(SaleRepository saleRepository) {
        super(saleRepository);
    }
}
