package org.springboot.trendmartecommerceplatform.Product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/products")
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "auth")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "get all products")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get products by id")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product productById = productService.getById(id);
        return ResponseEntity.ok(productById);
    }


    @PostMapping
    @Operation(summary = "Inserting a new product")
    public ResponseEntity<Product> addProduct(@RequestBody Dto dto) {
        Product newProduct = productService.addProduct(dto);
        return ResponseEntity.ok(newProduct);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/admin/edit/{id}")

    @Operation(summary = "Admin Updates/edits products")
    public ResponseEntity<Product> EditProduct(@Valid @RequestBody Dto dto, @PathVariable long id) {
        Product updateProduct = productService.updateProduct(id, dto);
        return ResponseEntity.ok(updateProduct);
    }


    @DeleteMapping
    @Operation(summary = "Delete product by id")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Deleted product with id " + id);
    }



}


