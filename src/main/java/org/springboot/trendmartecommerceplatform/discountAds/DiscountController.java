package org.springboot.trendmartecommerceplatform.discountAds;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.Product.Product;
import org.springboot.trendmartecommerceplatform.Product.ProductService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/discounts")
@RequiredArgsConstructor
@SecurityRequirement(name = "auth")
public class DiscountController {

    private final DiscountService discountService;
    private final ProductService productService;

    @PostMapping
    @Operation(summary = "create discount")
    public ResponseEntity<Discount> saveDiscount(@RequestBody DiscountDto dto) {
        Discount discount1 = discountService.createDiscount(dto);
        return new ResponseEntity<>(discount1, HttpStatus.CREATED);

    }
    @GetMapping
    @Operation(summary = "get all discount")
    public ResponseEntity<List<Discount>> getAllDiscount(){
      List<Discount> discount1 = discountService.getAllDiscounts();
                return new ResponseEntity<>(discount1,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get a specific discount by ID")
    public ResponseEntity<Discount> getDiscountById(@Valid @PathVariable Long id){
        Discount discount = discountService.getDiscountById(id);
        return new ResponseEntity<>(discount, HttpStatus.OK);

    }
    @PutMapping("/{id}")
    @Operation(summary = "Update a discount by ID ")
    public ResponseEntity<Discount> updateDiscountById(@Valid @PathVariable Long id ,@RequestBody DiscountDto dto){
        Discount discount1 = discountService.updateDiscount(id, dto);
        return new ResponseEntity<>(discount1, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a discount")
    public ResponseEntity<Discount> deleteDiscountById(@Valid @PathVariable Long id){
       discountService.deleteDiscount(id);
       return new ResponseEntity<>(HttpStatus.OK);

    }
    @PutMapping("/product/{productId}")
    public Product applyDiscountToProduct (
            @PathVariable Long productId,
            @RequestParam double percentage,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
        Product product = productService.getById(productId);
        Discount discount = product.getDiscount();


        discountService.updateDiscount(discount, percentage, startDate, endDate);
        return product;

    }






}
