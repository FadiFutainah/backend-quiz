package maids.quiz.salesms.repository;

import maids.quiz.salesms.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Client, Integer> {
}
