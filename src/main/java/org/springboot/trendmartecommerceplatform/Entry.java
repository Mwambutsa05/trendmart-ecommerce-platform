package org.springboot.trendmartecommerceplatform;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Entry {
    @SecurityRequirement(name = "auth")
    @GetMapping
    public String getEntry() {
        return "Welcome to Ecommerce Backend";
    }
}
