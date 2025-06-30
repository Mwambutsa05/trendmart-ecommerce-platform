package org.springboot.trendmartecommerceplatform.stock;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.Product.Product;
import org.springboot.trendmartecommerceplatform.Product.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
@SecurityRequirement(name = "auth")
public class StockController {
    private final StockService stockService;
    private final ProductService productService;

    @PostMapping("/product")
    @Operation(summary = "inserting new product in stock")
    @PreAuthorize("hasRole('ADMIN')")
    public Stock createStock(@RequestBody Dto dto, @PathVariable Long ProductId) {
         return stockService.addStock(dto, ProductId);
    }

    @GetMapping("/all/products")
    @Operation(summary = "all products in stock")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Stock> getAllStocks() {
        return stockService.findAllStocks();
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "get products in stock by its id ")
    @PreAuthorize("hasRole('ADMIN')")
    public Product getProductInStock(@PathVariable Long productId) {
        return stockService.getProductFromStockById(productId);
    }

   @PatchMapping("/edit/{id}")
   @Operation(summary = "admin edit product in stock")
   @PreAuthorize("hasRole('ADMIN')")
    public Stock editStock(@RequestBody Dto dto, @PathVariable Long productId) {
        return stockService.updateStock(dto, productId);
   }

   @DeleteMapping("/delete/{id}")
   @Operation(summary = "deletes product in stock")
   @PreAuthorize("hasRole('ADMIN')")
   public Stock deleteStock(@PathVariable Long productId) {
        return stockService.deleteStock(productId);
   }

}
