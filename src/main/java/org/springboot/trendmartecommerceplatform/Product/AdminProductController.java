package org.springboot.trendmartecommerceplatform.Product;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products")
@AllArgsConstructor
public class AdminProductController {
    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Admin adds products")
    public Product addProduct(@Valid @RequestBody Dto dto) {
        return productService.addProduct(dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/edit/{id}")
    @Operation(summary = "Admin Updates/edits products")
    public Product EditProduct(@Valid @RequestBody Dto dto, @PathVariable long id) {
        return productService.updateProduct(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/delete/{id}")
    @Operation(summary = "Admin delets products")
    public void DeleteProduct(@Valid  @PathVariable long id) {
        productService.deleteProduct(id);
    }

}

