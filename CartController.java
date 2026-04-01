package com.ecommerce.controller;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartRepository cartRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    @PostMapping("/add/{productId}")
    public Cart add(@PathVariable Long productId) {

        // ✅ Get logged-in user from JWT
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepo.findByUsername(username);

        // ✅ Get or create cart
        Cart cart = cartRepo.findByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }

        // ✅ Add product
        Product product = productRepo.findById(productId).orElseThrow();
        cart.getProducts().add(product);

        return cartRepo.save(cart);
    }
}
