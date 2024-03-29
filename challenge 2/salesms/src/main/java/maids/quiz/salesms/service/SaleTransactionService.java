package maids.quiz.salesms.service;

import lombok.RequiredArgsConstructor;
import maids.quiz.salesms.repository.SaleTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleTransactionService {
    final SaleTransactionRepository saleTransactionRepository;

    public Map<Long, Long> getTopTenMostRepeatedProducts(int limit) {
        List<Object[]> counts = saleTransactionRepository.findMostRepeatedProducts();

        return counts.stream()
                .limit(limit)  // Limit to top ten most repeated products
                .collect(Collectors.toMap(
                        arr -> (Long) arr[0],  // productId
                        arr -> (Long) arr[1]   // count
                ));
    }
}
