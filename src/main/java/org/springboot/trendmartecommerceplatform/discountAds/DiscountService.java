package org.springboot.trendmartecommerceplatform.discountAds;

import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.Product.Product;
import org.springboot.trendmartecommerceplatform.Product.ProductRepository;
import org.springboot.trendmartecommerceplatform.review.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DiscountService {
    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;

    public Discount createDiscount(DiscountDto dto) {
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        Discount discount = new Discount();
        discount.setTitle(dto.getTitle());
        discount.setDescription(dto.getDescription());
        discount.setDiscountPercentage(dto.getDiscountPercentage());
        discount.setStartDate(dto.getStartDate());
        discount.setEndDate(dto.getEndDate());
        discount.setProduct(product);
        discount.setActive(dto.getActive());
        return discountRepository.save(discount);

    }
    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }
    public Discount getDiscountById(Long id) {
        return discountRepository.findById(id).orElseThrow(() -> new RuntimeException("Discount not found"));

    }
    public Discount updateDiscount(Long id ,DiscountDto dto) {
        Discount discount = discountRepository.findById(id).orElseThrow(() -> new RuntimeException("Discount not found"));
        discount.setTitle(dto.getTitle());
        discount.setDescription(dto.getDescription());
        discount.setDiscountPercentage(dto.getDiscountPercentage());
        discount.setStartDate(dto.getStartDate());
        discount.setEndDate(dto.getEndDate());
        discount.setActive(dto.getActive());
        return discountRepository.save(discount);

    }
    public Discount updateDiscount(Discount discount, double percentage, LocalDate startDate, LocalDate endDate) {
        discount.setDiscountPercentage(percentage);
        discount.setStartDate(startDate);
        discount.setEndDate(endDate);
        return discountRepository.save(discount);

    }
    public void  deleteDiscount(Long id) {
        discountRepository.deleteById(id);
    }
}
