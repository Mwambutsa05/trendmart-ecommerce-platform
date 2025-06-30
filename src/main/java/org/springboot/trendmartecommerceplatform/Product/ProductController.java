package org.springboot.trendmartecommerceplatform.Product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "auth")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    @Operation(summary = "get all products")
    public List<Product> getProducts() {
        return productService.findAll();
    }

    @GetMapping("/products/{id}")
    @Operation(summary = "Get products by id")
    public Product getProduct(@PathVariable Long id) {
        return productService.getById(id);
    }


    @PostMapping
    @Operation(summary = "Inserting a new product")
    @PreAuthorize("hasRole('ADMIN')")
    public Product addProduct(@RequestBody Dto dto) {
        return productService.addProduct(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/admin/edit/{id}")
    @Operation(summary = "Admin Updates/edits products")
    public Product EditProduct(@Valid @RequestBody Dto dto, @PathVariable long id) {
        return productService.updateProduct(id, dto);
    }


    @DeleteMapping
    @Operation(summary = "Delete product by id")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
    }



}


