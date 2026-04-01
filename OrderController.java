package com.ecommerce.controller;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepo;
    private final CartRepository cartRepo;
    private final UserRepository userRepo;

    @PostMapping
    public Order placeOrder() {

        // ✅ Get logged-in user
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(username);

        // ✅ Get user cart
        Cart cart = cartRepo.findByUser(user);

        if (cart == null || cart.getProducts().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // ✅ Create order
        Order order = new Order();
        order.setUser(user);
        order.setProducts(cart.getProducts());

        double total = cart.getProducts()
                .stream()
                .mapToDouble(p -> p.getPrice())
                .sum();

        order.setTotalPrice(total);

        // ✅ Add status (IMPORTANT for review)
        order.setStatus("CREATED");

        // OPTIONAL: clear cart
        cart.getProducts().clear();
        cartRepo.save(cart);

        return orderRepo.save(order);
    }
}
