package slava.bank.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import slava.bank.repositories.ClientRepository;

import java.math.BigDecimal;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class ClientUpdateService {

    private final ClientRepository clientRepository;

    public ClientUpdateService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void reduceBalance(long id, BigDecimal sum) {
        clientRepository.reduceBalance(id, sum);
    }

    public void increaseBalance(long id, BigDecimal sum) {
        clientRepository.increaseBalance(id, sum);
    }

    public void transfer(long userId, long receiverId, BigDecimal sum) {
        if (userId < receiverId) {
            clientRepository.reduceBalance(userId, sum);
            clientRepository.increaseBalance(receiverId, sum);
        } else {
            clientRepository.increaseBalance(receiverId, sum);
            clientRepository.reduceBalance(userId, sum);
        }
     }

}
