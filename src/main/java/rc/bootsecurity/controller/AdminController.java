package rc.bootsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rc.bootsecurity.db.UserRepository;

@Controller
@RequestMapping("admin")
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("index")
    public String index(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "admin/index";
    }

}
