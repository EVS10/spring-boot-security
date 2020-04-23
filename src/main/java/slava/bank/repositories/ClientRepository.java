package slava.bank.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import slava.bank.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUsername(String username);
    Client findById(long id);
    List<Client> findAllByOrderById();
}
