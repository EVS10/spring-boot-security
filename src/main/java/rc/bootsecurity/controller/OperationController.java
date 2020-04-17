package rc.bootsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("operation")
public class OperationController {

    @RequestMapping("/add")
    public String add() {
        System.out.println("ADD");
        return "operation/add";
    }

    @RequestMapping("/withdraw")
    public String withdraw() {
        System.out.println("WITHDRAW");
        return "operation/withdraw";
    }

    @RequestMapping("/transfer")
    public String transfer() {
        System.out.println("TRANSFER");
        return "operation/transfer";
    }

}
