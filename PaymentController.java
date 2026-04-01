package com.ecommerce.controller;

import com.ecommerce.entity.Order;
import com.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final OrderRepository orderRepo;

    @PostMapping("/{orderId}")
    public String pay(@PathVariable Long orderId) {

        // ✅ Get order
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // ✅ Update payment status
        order.setStatus("PAID");

        orderRepo.save(order);

        return "Payment Successful for Order ID: " + orderId;
    }
}
