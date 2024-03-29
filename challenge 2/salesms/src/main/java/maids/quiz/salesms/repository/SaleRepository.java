package maids.quiz.salesms.repository;

import maids.quiz.salesms.model.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
    @Query("SELECT e FROM Sale e WHERE e.createdAt BETWEEN :fromDate AND :toDate")
    Page<Sale> findByCreatedAtBetween(
            @Param("fromDate") Instant fromDate,
            @Param("toDate") Instant toDate,
            Pageable pageable
    );
    Long countByCreatedAtBetween(Instant fromDate, Instant toDate);

    @Query(value = "SELECT SUM(total) FROM Sale WHERE createdAt BETWEEN :fromDate AND :toDate")
    Long sumTotalByCreatedAtBetween(@Param("fromDate") Instant fromDate, @Param("toDate") Instant toDate);


    @Query("SELECT s.seller, SUM(s.total) AS totalSum " +
            "FROM Sale s " +
            "GROUP BY s.seller " +
            "ORDER BY totalSum DESC")
    List<Object[]> findSellersOrderByTotalSum();
}
