package rc.bootsecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rc.bootsecurity.db.UserRepository;
import rc.bootsecurity.model.User;

import java.math.BigDecimal;

@Controller
@RequestMapping("operation")
public class OperationController {

    private final UserRepository userRepository;

    public OperationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
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
    public String plus(@RequestParam BigDecimal sum) {
        User user = getCurrentUser();
        user.addBalance(sum);
        userRepository.save(user);
        return "operation/success";
    }

}
