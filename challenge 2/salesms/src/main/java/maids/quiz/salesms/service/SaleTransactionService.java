package maids.quiz.salesms.service;

import lombok.RequiredArgsConstructor;
import maids.quiz.salesms.model.Product;
import maids.quiz.salesms.repository.SaleTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleTransactionService {
    final SaleTransactionRepository saleTransactionRepository;


    public List<Product> getMostRepeatedProducts() {
        List<Object[]> results = saleTransactionRepository.findMostRepeatedProducts();

        return results.stream()
                .map(result -> (Product) result[0])
                .collect(Collectors.toList());
    }
}
