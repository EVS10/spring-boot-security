package rc.bootsecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rc.bootsecurity.repositories.ClientRepository;
import rc.bootsecurity.model.Client;

import java.math.BigDecimal;
import java.util.Arrays;

@Controller
@RequestMapping("operation")
public class OperationController {

    private String successOperation = "Operation successful!";
    private String failureOperation = "Operation failed!";
    private String resultPage = "operation/result";

    private final ClientRepository clientRepository;

    public OperationController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    private Client getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return clientRepository.findByUsername(username);
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
        Client user = getCurrentUser();
        String message = "";
        try {
            if (user.add(new BigDecimal(sum))) {
                clientRepository.save(user);
                message = successOperation;
            } else {
                message = failureOperation + " Sum must be positive";
            }
        } catch (NumberFormatException ex) {
                message = failureOperation + " Wrong input format";
        }
        model.addAttribute("message", message);
        return resultPage;
    }

    @PostMapping("/perform/withdraw")
    public String withdraw(@RequestParam String sum, Model model) {
        Client user = getCurrentUser();
        String message = null;
        try {
            if (user.withdraw(new BigDecimal(sum))) {
                clientRepository.save(user);
                message = successOperation;
            } else if (sum.contains("-")) {
                message = failureOperation + " Sum must be positive";
            } else {
                message = failureOperation + " Not enough money";
            }
        } catch (NumberFormatException ex) {
            message = failureOperation + " Wrong input format";
        }
        model.addAttribute("message", message);
        return resultPage;
    }

    @PostMapping("/perform/transfer")
    public String withdraw(@RequestParam String id, @RequestParam String sum, Model model) {
        Client user = getCurrentUser();
        String message = null;
        Client receiver = clientRepository.findById(Long.parseLong(id));
        try {
            if (receiver != null && user.transfer(receiver, new BigDecimal(sum))) {
                clientRepository.saveAll(Arrays.asList(user, receiver));
                message = successOperation;
            } else if (receiver == null) {
                message = failureOperation + " Receiver ID does not exist";
            } else if (sum.contains("-")) {
                message = failureOperation + " Sum must be positive";
            } else {
                message = failureOperation + " Not enough money";
            }
        } catch (NumberFormatException ex) {
            message = failureOperation + "Wrong input format";
        }
        model.addAttribute("message", message);
        return resultPage;
    }

}
