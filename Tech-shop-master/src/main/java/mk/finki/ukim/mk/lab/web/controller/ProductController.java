package mk.finki.ukim.mk.lab.web.controller;

import mk.finki.ukim.mk.lab.model.Category;
import mk.finki.ukim.mk.lab.model.Product;
import mk.finki.ukim.mk.lab.service.CategoryService;
import mk.finki.ukim.mk.lab.service.ManufacturerService;
import mk.finki.ukim.mk.lab.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ManufacturerService manufacturerService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, ManufacturerService manufacturerService,
                             CategoryService categoryService) {
        this.productService = productService;
        this.manufacturerService = manufacturerService;
        this.categoryService = categoryService;
    }


    @GetMapping
    public String getProductsPage(@RequestParam(required = false) String error,
                                  @RequestParam(required = false) String searchTerm,
                                  @RequestParam(required = false) String searchByStatus,
                                  Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        if (searchTerm != null) {
            model.addAttribute("products", this.productService.searchByNameOrDescription(searchTerm));
        } else if (searchByStatus != null) {
          //  model.addAttribute("products", this.productService.findByStatus(ProductStatus.valueOf(searchByStatus)));
        } else {
            model.addAttribute("products", this.productService.listProducts());
        }
        model.addAttribute("bodyContent", "listProducts");
        return "master-template";
    }

    @GetMapping("/add-form")
    public String getAddNewBalloonPage(Model model) {
        model.addAttribute("product", new Product());
      //  model.addAttribute("statuses", this.productService.findAllStatuses());
        model.addAttribute("manufacturers", this.manufacturerService.findAll());
        model.addAttribute("categories", this.categoryService.listCategories());
        model.addAttribute("bodyContent", "addProduct");
        return "master-template";
    }

    @GetMapping("/edit-form/{id}")
    public String getEditBalloonPage(@PathVariable Long id, Model model) {
        Product product = this.productService.findById(id).get();
        model.addAttribute("product", product);
//        model.addAttribute("statuses", this.productService.findAllStatuses());
        model.addAttribute("manufacturers", this.manufacturerService.findAll());
        model.addAttribute("categories", this.categoryService.listCategories());
        model.addAttribute("bodyContent", "addProduct");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveProduct(@RequestParam(required = false) Long id,
                              @RequestParam String name,
                              @RequestParam String description,
                              @RequestParam Double price,
                              @RequestParam Integer quantity,
                              @RequestParam List<Long> categoryIds,
                              @RequestParam Long manufacturerId) {

        if (id != null) {
            this.productService.edit(id, name, description, price, quantity, categoryIds, manufacturerId);
        } else {
            this.productService.save(name, description, price, quantity, categoryIds, manufacturerId);
        }
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteBalloon(@PathVariable Long id) {
        this.productService.deleteById(id);
        return "redirect:/products";
    }

//    @PostMapping
//    public String saveBalloon(@RequestParam Long id, @RequestParam String name, @RequestParam String description) {
//        return null;
//    }
//
//    @PutMapping("/{id}")
//    public String updateBalloon(@PathVariable Long id, Balloon balloon) {
//        return null;
//    }


}
