package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping
    public String getOrdersPage(HttpServletRequest req, Model model) {
        String username = req.getRemoteUser();
//        List<Order> orders = orderService.listAllOrders();
        List<Order> orders = orderService.listOrdersByUsername(username);
        model.addAttribute("orders", orders);
        model.addAttribute("bodyContent", "listOrders");
        return "master-template";
    }

    @PostMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id){
        this.orderService.deleteById(id);
        return "redirect:/orders";
    }
}
