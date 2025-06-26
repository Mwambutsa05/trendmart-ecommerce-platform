package org.springboot.trendmartecommerceplatform.review;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.SeparatorUI;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@SecurityRequirement(name = "auth")
public class ReviewController {
    private final ReviewService reviewService;

@PostMapping
    @Operation(summary = "create review")
    public ResponseEntity<Review> createReview( @Valid @RequestBody ReviewDto Dto){
    Review review = reviewService.addReview(Dto);
    return new ResponseEntity<>(review, HttpStatus.CREATED);

}
@GetMapping
    @Operation(summary = "Get all review")
    public ResponseEntity<List<Review>> getAllReview(){
    List<Review> reviews = reviewService.findAllReviews();
    return new ResponseEntity<>(reviews, HttpStatus.OK);

}
@GetMapping("/{id}")
    @Operation( summary = "get review by Id/")
    public ResponseEntity<Review> getReview(@Valid @PathVariable Long id){
    Review review = reviewService.findReviewById(id);
    return new ResponseEntity<>(review, HttpStatus.OK);
}
@GetMapping("/product/{productId}")
    @Operation(summary = "get review by productId")
    public ResponseEntity<Review> getReviewByProductId(@Valid @PathVariable Long productId){
    Review review = reviewService.findReviewByProductId(productId);
    return new ResponseEntity<>(review, HttpStatus.OK);
}


@DeleteMapping("/{id}")
    @Operation(summary = "delete a review")
    public ResponseEntity<Review> deleteReview(@Valid @PathVariable Long id){
    reviewService.deleteReview(id);
    return new ResponseEntity<>(HttpStatus.OK);
}




}
