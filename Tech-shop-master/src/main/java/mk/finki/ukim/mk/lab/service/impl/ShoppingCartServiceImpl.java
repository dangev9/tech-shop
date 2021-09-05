package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.ShoppingCart;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.enumerations.ShoppingCartStatus;
import mk.finki.ukim.mk.lab.model.exception.NoItemsInCartException;
import mk.finki.ukim.mk.lab.model.exception.OrderAlreadyExistsException;
import mk.finki.ukim.mk.lab.model.exception.OrderDoesNotExistException;
import mk.finki.ukim.mk.lab.model.exception.UserNotFoundException;
import mk.finki.ukim.mk.lab.repository.ShoppingCartRepository;
import mk.finki.ukim.mk.lab.repository.UserRepository;
import mk.finki.ukim.mk.lab.service.OrderService;
import mk.finki.ukim.mk.lab.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   UserRepository userRepository,
                                   OrderService orderService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.orderService = orderService;
    }

    @Override
    public List<Order> listAllOrdersInShoppingCart(Long cartId) {
        ShoppingCart shoppingCart = this.shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new NoItemsInCartException(cartId));
        return shoppingCart.getOrders();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return this.shoppingCartRepository.findAllByUserAndStatus(user, ShoppingCartStatus.CREATED)
                .orElseGet(() -> this.shoppingCartRepository.save(new ShoppingCart(user)));
    }

    @Override
    public ShoppingCart addOrderToShoppingCart(String username, Long orderId) {
        ShoppingCart cart = this.getActiveShoppingCart(username);
        Order order = this.orderService.findById(orderId)
                .orElseThrow(() -> new OrderDoesNotExistException(orderId));
        if (cart.getOrders()
                .stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .collect(Collectors.toList()).size() > 0)
            throw new OrderAlreadyExistsException(orderId);
        cart.getOrders().add(order);
        return this.shoppingCartRepository.save(cart);
    }

    @Override
    public void deleteOrderFromShoppingCart(String username, Long orderId) {
        ShoppingCart cart = this.getActiveShoppingCart(username);
        Order order = this.orderService.findById(orderId)
                .orElseThrow(() -> new OrderDoesNotExistException(orderId));
        cart.getOrders().remove(order);
//        this.shoppingCartRepository.deleteByOrders(order);
    }
}
