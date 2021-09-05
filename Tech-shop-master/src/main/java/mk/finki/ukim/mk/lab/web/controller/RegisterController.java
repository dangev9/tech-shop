package mk.finki.ukim.mk.lab.web.controller;


import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.exception.UsernameAlreadyExistsException;
import mk.finki.ukim.mk.lab.service.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final AuthenticationService authenticationService;

    public RegisterController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model){
        if (error != null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        return "register";
    }

    @PostMapping
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String repeatedPassword,
                               @RequestParam String name,
                               @RequestParam String surname,
                               HttpServletRequest request){
        try{
            User user = this.authenticationService.register(username, password,repeatedPassword, name, surname);
            request.getSession().setAttribute("user", user);
            return "redirect:/login";
        } catch (UsernameAlreadyExistsException ex){
            return "redirect:/register?error=" + ex.getMessage();
        }
    }
}
