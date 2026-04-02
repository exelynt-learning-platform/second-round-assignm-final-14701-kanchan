
package com.ecommerce.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @PostMapping
    public String pay() {
        return "Payment Successful";
    }
}
