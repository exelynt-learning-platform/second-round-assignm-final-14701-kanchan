
package com.ecommerce.controller;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.Product;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartRepository cartRepo;
    private final ProductRepository productRepo;

    @PostMapping("/{userId}/add/{productId}")
    public Cart add(@PathVariable Long userId, @PathVariable Long productId) {
        Cart cart = cartRepo.findByUserId(userId);
        Product p = productRepo.findById(productId).orElseThrow();
        cart.getProducts().add(p);
        return cartRepo.save(cart);
    }
}
