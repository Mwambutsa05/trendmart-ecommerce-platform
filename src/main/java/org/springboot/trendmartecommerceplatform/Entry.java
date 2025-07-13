package org.springboot.trendmartecommerceplatform;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Entry {
    @GetMapping
    public String getEntry() {
        return "Welcome to Ecommerce Backend";
    }
}
