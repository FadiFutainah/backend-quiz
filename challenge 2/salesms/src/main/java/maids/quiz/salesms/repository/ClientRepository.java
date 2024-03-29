package maids.quiz.salesms.repository;

import maids.quiz.salesms.model.Client;
import maids.quiz.salesms.model.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

  Optional<Client> findByEmail(String email);

  @Query("SELECT e FROM Client e WHERE e.createdAt BETWEEN :fromDate AND :toDate")
  Page<Sale> findByCreatedAtBetween(
          @Param("fromDate") Instant fromDate,
          @Param("toDate") Instant toDate,
          Pageable pageable
  );
  Long countByCreatedAtBetween(Instant fromDate, Instant toDate);

}
