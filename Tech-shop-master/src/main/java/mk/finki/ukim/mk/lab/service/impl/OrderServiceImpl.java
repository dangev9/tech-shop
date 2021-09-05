package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.repository.OrderRepository;
import mk.finki.ukim.mk.lab.service.OrderService;
import mk.finki.ukim.mk.lab.service.ShoppingCartService;
import mk.finki.ukim.mk.lab.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    @Override
    public Order placeOrder(String productName, String payment, User user) {
        Order order = new Order();
        order.setProductName(productName);
        order.setPayment(payment);
        user = this.userService.save(user);
        order.setUser(user);
        return this.orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> listAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> listOrdersByUsername(String username) {
        return this.orderRepository.findAllByUserName(username);
    }

    @Override
    public void deleteById(Long id) {
        this.orderRepository.deleteById(id);
    }
}
