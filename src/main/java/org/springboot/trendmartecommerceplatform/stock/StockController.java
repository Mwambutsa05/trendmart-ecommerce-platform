package org.springboot.trendmartecommerceplatform.stock;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.Product.Product;
import org.springboot.trendmartecommerceplatform.Product.ProductService;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("{productId}")
    @Operation(summary = "inserting new product in stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Stock> createStock(@RequestBody Dto dto, @PathVariable Long productId) {
         Stock newStock = stockService.addStock(dto, productId);
         return ResponseEntity.ok(newStock);
    }

    @GetMapping("/products")
    @Operation(summary = "all products in stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> Stocks = stockService.findAllStocks();
        return ResponseEntity.ok(Stocks);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "get products in stock by its id ")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> getProductInStock(@PathVariable Long productId) {
        Product productsInStock = stockService.getProductFromStockById(productId);
        return ResponseEntity.ok(productsInStock);
    }

   @PatchMapping("/{productId}")
   @Operation(summary = "admin edit product in stock")
   @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Stock> editStock(@RequestBody Dto dto, @PathVariable Long productId) {
        Stock updateStock = stockService.updateStock(dto, productId);
        return ResponseEntity.ok(updateStock);
   }

   @DeleteMapping("/{productId}")
   @Operation(summary = "deletes product in stock")
   @PreAuthorize("hasRole('ADMIN')")
   public ResponseEntity<String> deleteStock(@PathVariable Long productId) {
        stockService.deleteStock(productId);
        return ResponseEntity.ok("Deleted product with id " + productId);
   }

}
