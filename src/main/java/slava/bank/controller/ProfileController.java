package slava.bank.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import slava.bank.repositories.ClientRepository;
import slava.bank.model.Client;

@Controller
@RequestMapping("profile")
public class ProfileController {

    private final ClientRepository clientRepository;

    public ProfileController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("index")
    public String index(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Client user = clientRepository.findByUsername(username);
        model.addAttribute("user", user);
        return "profile/index";
    }
}
