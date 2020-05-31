package slava.bank.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import slava.bank.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUsername(String username);
    Client findById(long id);
    List<Client> findAllByOrderById();

    @Modifying
    @Query("update Client set balance = balance + ?2 where id = ?1")
    void increaseBalance(long id, BigDecimal sum);

    @Modifying
    @Query("update Client set balance = balance - ?2 where id = ?1")
    void reduceBalance(long id, BigDecimal sum);
}
