package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Manufacturer;
import mk.finki.ukim.mk.lab.service.ManufacturerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manufacturers")
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    public ManufacturerController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public String getManufacturersPage(Model model){
        model.addAttribute("manufacturers", this.manufacturerService.findAll());
        model.addAttribute("bodyContent", "manufacturers");
        return "master-template";
    }
}
