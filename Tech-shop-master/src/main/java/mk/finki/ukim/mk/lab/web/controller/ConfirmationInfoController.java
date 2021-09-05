package mk.finki.ukim.mk.lab.web.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/ConfirmationInfo")
public class ConfirmationInfoController {

    @GetMapping
    public String show(HttpServletRequest req, Model model) {
        String clientAddress = (String) req.getSession().getAttribute("clientAddress");
        req.getSession().setAttribute("clientAddress", clientAddress);
        String clientName = (String) req.getSession().getAttribute("username");
        req.getSession().setAttribute("username", clientName);
        model.addAttribute("bodyContent", "confirmationInfo");
        return "master-template";
    }

    @PostMapping
    public String invalidate(HttpServletRequest req) {
        req.getSession().invalidate();
        return "redirect:/products";
    }
}
