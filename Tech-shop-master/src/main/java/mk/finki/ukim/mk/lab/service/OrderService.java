package mk.finki.ukim.mk.lab.service;

import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order placeOrder(String productName, String payment, User user);
    Optional<Order> findById(Long id);
    List<Order> listAllOrders();
    List<Order> listOrdersByUsername(String username);
    void deleteById(Long id);
}
