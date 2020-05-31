package slava.bank.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import slava.bank.repositories.ClientRepository;
import slava.bank.model.Client;
import slava.bank.service.ClientUpdateService;

import java.math.BigDecimal;

@Controller
@RequestMapping("operation")
public class OperationController {

    private String successOperation = "Operation successful!";
    private String failureOperation = "Operation failed!";
    private String resultPage = "operation/result";

    private final ClientRepository clientRepository;
    private final ClientUpdateService clientUpdateService;

    public OperationController(ClientRepository clientRepository, ClientUpdateService clientUpdateService) {
        this.clientRepository = clientRepository;
        this.clientUpdateService = clientUpdateService;
    }

    private Client getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return clientRepository.findByUsername(username);
    }

    private long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    @RequestMapping("/add")
    public String add() {
        return "operation/add";
    }

    @RequestMapping("/withdraw")
    public String withdraw() {
        return "operation/withdraw";
    }

    @RequestMapping("/transfer")
    public String transfer() {
        return "operation/transfer";
    }

    @PostMapping("/perform/add")
    public String add(@RequestParam String sum, Model model) {
        String message = "";
        try {
            if (sum.contains("-")) {
                message = failureOperation + " Sum must be positive";
            } else {
                clientUpdateService.increaseBalance(getCurrentUserId(), new BigDecimal(sum));
                message = successOperation;
            }
        } catch (NumberFormatException ex) {
                message = failureOperation + " Wrong input format";
        }
        model.addAttribute("message", message);
        return resultPage;
    }

    @PostMapping("/perform/withdraw")
    public String withdraw(@RequestParam String sum, Model model) {
        String message = "";
        try {
            if (sum.contains("-")) {
                message = failureOperation + " Sum must be positive";
            } else {
                clientUpdateService.reduceBalance(getCurrentUserId(), new BigDecimal(sum));
                message = successOperation;
            }
        } catch (NumberFormatException ex) {
            message = failureOperation + " Wrong input format";
        } catch (RuntimeException ex) {
            message = failureOperation + " Not enough money";
        }
        model.addAttribute("message", message);
        return resultPage;
    }

    @PostMapping("/perform/transfer")
    public String transfer(@RequestParam String id, @RequestParam String sum, Model model) {
        String message = "";
        try {
            Client receiver = clientRepository.findById(Long.parseLong(id));
            if (receiver == null) {
                message = failureOperation + " Receiver ID does not exist";
            } else if (sum.contains("-")) {
                message = failureOperation + " Sum must be positive";
            } else {
                clientUpdateService.transfer(getCurrentUserId(), receiver.getId(), new BigDecimal(sum));
                message = successOperation;
            }
        } catch (NumberFormatException ex) {
            message = failureOperation + " Wrong input format";
        } catch (RuntimeException ex) {
            message = failureOperation + " Not enough money";
        }
        model.addAttribute("message", message);
        return resultPage;
    }

}
