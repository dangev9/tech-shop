package mk.finki.ukim.mk.lab.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

//2 baranje
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String productName;

    private String payment;

    @ManyToMany(mappedBy = "orders")
    private List<ShoppingCart> carts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



}
