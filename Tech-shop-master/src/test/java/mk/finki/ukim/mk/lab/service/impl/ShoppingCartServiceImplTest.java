package mk.finki.ukim.mk.lab.service.impl;

import mk.finki.ukim.mk.lab.model.Order;
import mk.finki.ukim.mk.lab.model.ShoppingCart;
import mk.finki.ukim.mk.lab.model.User;
import mk.finki.ukim.mk.lab.model.exception.NoItemsInCartException;
import mk.finki.ukim.mk.lab.model.exception.UserNotFoundException;
import mk.finki.ukim.mk.lab.repository.ShoppingCartRepository;
import mk.finki.ukim.mk.lab.repository.UserRepository;
import mk.finki.ukim.mk.lab.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {

    @Mock
    ShoppingCartRepository shoppingCartRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    OrderService orderService;

    @InjectMocks
    ShoppingCartServiceImpl service;

    @ParameterizedTest
    @CsvSource({
            "false, true",
            "true, false"
    })
    void listAllOrdersInShoppingCart(boolean exception, boolean exists) {
        //given
        if (exists) {
            ShoppingCart cart = new ShoppingCart();
            when(this.shoppingCartRepository.findById(anyLong())).thenReturn(Optional.of(cart));
        } else {
            when(this.shoppingCartRepository.findById(anyLong())).thenReturn(Optional.empty());
        }

        NoItemsInCartException ex = null;
        List<Order> list = null;
        //when
        try {
            list = this.service.listAllOrdersInShoppingCart(12421L);
        } catch (NoItemsInCartException e) {
            ex = e;
        }

        //then
        if (exception) {
            assertNull(list);
            assertNotNull(ex);
        } else {
            assertNotNull(list);
            assertNull(ex);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "false, true, true",
            "false, true, false",
            "true, false, false",
            "true, false, true"
    })
    void getActiveShoppingCart(boolean shouldThrow, boolean userExists, boolean shoppingCartExists) {
        //given
        if (userExists) {
            User user = new User();
            when(this.userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        } else {
            when(this.userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        }

        ShoppingCart shoppingCart = new ShoppingCart();
        lenient().when(this.shoppingCartRepository.findAllByUserAndStatus(any(),any())).then(i -> {
            if (shoppingCartExists) {
                shoppingCart.setId(1L);
                return Optional.of(shoppingCart);
            } else {
                shoppingCart.setId(2L);
                return Optional.empty();
            }
        });

        lenient().when(this.shoppingCartRepository.save(any())).thenReturn(shoppingCart);


        //when
        UserNotFoundException ex = null;
        ShoppingCart sc = null;
        try {
            sc = this.service.getActiveShoppingCart("temp");
        } catch (UserNotFoundException e) {
            ex = e;
        }

        //then
        if (shouldThrow) {
            assertNotNull(ex);
            assertNull(sc);
        } else {
            assertNull(ex);
            if (shoppingCartExists) {
                assertEquals(1L, sc.getId());
            } else {
                assertEquals(2L, sc.getId());
            }
        }

    }
}