package rc.bootsecurity.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rc.bootsecurity.model.Client;

@Repository
public interface UserRepository extends JpaRepository<Client, Long> {
    Client findByUsername(String username);
    Client findById(long id);
    List<Client> findAllByOrderById();
}
