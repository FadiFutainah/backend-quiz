package maids.quiz.salesms.repository;


import maids.quiz.salesms.model.SaleTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleTransactionRepository extends JpaRepository<SaleTransaction, Long> {
    @Query("SELECT st.product, COUNT(st.product) AS count " +
            "FROM SaleTransaction st " +
            "GROUP BY st.product " +
            "ORDER BY count DESC")
    List<Object[]> findMostRepeatedProducts();

    @Query("SELECT st.product, MAX(st.createdAt) AS lastTransactionDate " +
            "FROM SaleTransaction st " +
            "GROUP BY st.product " +
            "ORDER BY lastTransactionDate DESC")
    List<Object[]> findProductsOrderByLastTransactionDate();
}