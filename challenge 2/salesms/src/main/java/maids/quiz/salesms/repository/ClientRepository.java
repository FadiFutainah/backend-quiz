package maids.quiz.salesms.repository;

import maids.quiz.salesms.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {

  Optional<Client> findByEmail(String email);

}
