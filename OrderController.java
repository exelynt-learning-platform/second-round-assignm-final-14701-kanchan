
package com.ecommerce.controller;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Order;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepo;
    private final CartRepository cartRepo;

    @PostMapping("/{userId}")
    public Order placeOrder(@PathVariable Long userId) {
        Cart cart = cartRepo.findByUserId(userId);
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setProducts(cart.getProducts());
        double total = cart.getProducts().stream().mapToDouble(p -> p.getPrice()).sum();
        order.setTotalPrice(total);
        return orderRepo.save(order);
    }
}
