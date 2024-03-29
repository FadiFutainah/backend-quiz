package maids.quiz.salesms.repository;


import maids.quiz.salesms.model.SaleTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleTransactionRepository extends JpaRepository<SaleTransaction, Long> {

    @Query("SELECT t.productId, COUNT(t.productId) FROM SaleTransaction t " +
            "GROUP BY t.productId ORDER BY COUNT(t.productId) DESC")
    List<Object[]> findMostRepeatedProducts();
}