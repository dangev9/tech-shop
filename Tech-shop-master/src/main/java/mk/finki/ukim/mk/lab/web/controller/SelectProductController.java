package mk.finki.ukim.mk.lab.web.controller;

import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;

@Controller
@RequestMapping("/selectProduct")
public class SelectProductController {

    @GetMapping
    public String selectTheProduct(HttpServletRequest req, Model model) {
        String productName = req.getParameter("productName");
        req.getSession().setAttribute("productName", productName);
        model.addAttribute("bodyContent", "selectProductPaymentType");
        return "master-template";
    }

    @PostMapping
    public String selectSize(HttpServletRequest req, Model model) {
        String size = req.getParameter("payment");
        req.getSession().setAttribute("payment", size);
        return "redirect:/ProductOrder";
    }
}
