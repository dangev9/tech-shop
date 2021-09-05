package mk.finki.ukim.mk.lab.web.controller;


import mk.finki.ukim.mk.lab.model.ShoppingCart;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.service.OrderService;
import mk.finki.ukim.mk.lab.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final OrderService orderService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, OrderService orderService) {
        this.shoppingCartService = shoppingCartService;
        this.orderService = orderService;
    }

    @GetMapping
    public String getShoppingCartPage(@RequestParam(required = false) String error,
                                      HttpServletRequest req,
                                      Model model){
        if (error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        String username = req.getRemoteUser();
        ShoppingCart cart = this.shoppingCartService.getActiveShoppingCart(username);
        String clientAddress = (String) req.getSession().getAttribute("clientAddress");
        req.getSession().setAttribute("clientAddress", clientAddress);
        String productName = req.getParameter("productName");
        req.getSession().setAttribute("productName", productName);
        String size = req.getParameter("payment");
        req.getSession().setAttribute("payment", size);
        model.addAttribute("orders", this.shoppingCartService.listAllOrdersInShoppingCart(cart.getId()));
        model.addAttribute("bodyContent", "shopping-cart");
        return "master-template";
    }

    @PostMapping("/add-order/{id}")
    public String addProductToShoppingCart(@PathVariable Long id, HttpServletRequest req){
        try{
            ShoppingCart shoppingCart = this.shoppingCartService.addOrderToShoppingCart(req.getRemoteUser(), id);
            return "redirect:/shopping-cart";
        } catch (RuntimeException ex){
            return "redirect:/shopping-cart?error=" + ex.getMessage();
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteFromShoppingCart(@PathVariable Long id, HttpServletRequest req){
        String username = req.getRemoteUser();
        this.shoppingCartService.deleteOrderFromShoppingCart(username, id);
        return "redirect:/shopping-cart";
    }
}

