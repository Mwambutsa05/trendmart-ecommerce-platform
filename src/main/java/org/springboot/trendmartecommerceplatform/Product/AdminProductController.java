package org.springboot.trendmartecommerceplatform.Product;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminProductController {



}




//
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping
//    public Product addProduct(@RequestBody Product product) {
//        // Only admin can add
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @PutMapping("/{id}")
//    public Product editProduct(@PathVariable Long id, @RequestBody Product product) {
//        // Only admin can edit
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping("/{id}")
//    public void deleteProduct(@PathVariable Long id) {
//        // Only admin can delete
//    }
//}