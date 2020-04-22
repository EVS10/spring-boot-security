package rc.bootsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rc.bootsecurity.repositories.ClientRepository;

@Controller
@RequestMapping("admin")
public class AdminController {

    private final ClientRepository clientRepository;

    public AdminController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("clients")
    public String index(Model model) {
        model.addAttribute("users", clientRepository.findAllByOrderById());
        return "admin/clients";
    }

}
