package rc.bootsecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rc.bootsecurity.db.UserRepository;
import rc.bootsecurity.model.Client;

import java.math.BigDecimal;
import java.util.Arrays;

@Controller
@RequestMapping("operation")
public class OperationController {

    private final UserRepository userRepository;

    public OperationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private Client getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username);
    }

    @RequestMapping("/view/add")
    public String add() {
        return "operation/view/add";
    }

    @RequestMapping("/view/withdraw")
    public String withdraw() {
        return "operation/view/withdraw";
    }

    @RequestMapping("/view/transfer")
    public String transfer() {
        return "operation/view/transfer";
    }

    @PostMapping("/perform/add")
    public String add(@RequestParam BigDecimal sum) {
        Client user = getCurrentUser();
        user.add(sum);
        userRepository.save(user);
        return "operation/success";
    }

    @PostMapping("/perform/withdraw")
    public String withdraw(@RequestParam BigDecimal sum) {
        Client user = getCurrentUser();
        user.withdraw(sum);
        userRepository.save(user);
        return "operation/success";
    }

    @PostMapping("/perform/transfer")
    public String withdraw(@RequestParam long id, @RequestParam BigDecimal sum) {
        Client user = getCurrentUser();
        Client receiver = userRepository.findById(id);
        user.transfer(receiver, sum);
        userRepository.saveAll(Arrays.asList(user, receiver));
        return "operation/success";
    }

}
