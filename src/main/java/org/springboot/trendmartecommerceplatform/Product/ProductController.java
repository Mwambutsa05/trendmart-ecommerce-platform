package org.springboot.trendmartecommerceplatform.Product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
    public Product addProduct(@RequestBody Dto dto) {
        return productService.addProduct(dto);
    }
    @PatchMapping("/update/{id}")
    @Operation(summary = "Update products")
    public Product updateProduct(@RequestBody Dto dto, @PathVariable long id) {
        return productService.updateProduct(id, dto);
    }

    @DeleteMapping
    @Operation(summary = "Delete product by id")
    public void deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
    }



}
