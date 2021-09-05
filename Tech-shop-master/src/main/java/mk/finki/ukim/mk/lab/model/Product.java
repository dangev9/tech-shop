package mk.finki.ukim.mk.lab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tech_products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 5000)
    private String description;

    private Double price;

    private Integer quantity;

    @ManyToMany
    private List<Category> categories;

    @ManyToOne
    private Manufacturer manufacturer;

    public Product(String name, String description, Double price, Integer quantity, List<Category> categories, Manufacturer manufacturer) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.categories = categories;
        this.manufacturer = manufacturer;
    }

}
