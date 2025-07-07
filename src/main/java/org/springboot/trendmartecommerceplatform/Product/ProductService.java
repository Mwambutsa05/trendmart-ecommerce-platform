package org.springboot.trendmartecommerceplatform.Product;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springboot.trendmartecommerceplatform.discountAds.Discount;
import org.springboot.trendmartecommerceplatform.discountAds.DiscountDto;
import org.springboot.trendmartecommerceplatform.discountAds.DiscountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }
    public Product addProduct(Dto dto) {
//        create default discount
        Discount discount = Discount.builder()
                .discountPercentage(0.0)
                .build();
        discountRepository.save(discount);


        Product productToAdd = new Product();
        productToAdd.setName(dto.getName());
        productToAdd.setDescription(dto.getDescription());
        productToAdd.setPrice(dto.getPrice());

        productToAdd.setOriginalPrice(dto.getOriginalPrice());
        productToAdd.setImageUrls(dto.getImageUrls());
        productToAdd.setQuantity(dto.getQuantity());
        productToAdd.setSkuCode(dto.getSkuCode());
        productToAdd.setBrand(dto.getBrand());
        productToAdd.setDiscount(discount);


        return productRepository.save(productToAdd);
    }
    public Product updateProduct(long id, Dto dto) {          //Edit
       Product product = productRepository.findById(id).orElseThrow();
       product.setName(dto.getName());
       product.setDescription(dto.getDescription());
       product.setPrice(dto.getPrice());
       product.setOriginalPrice(dto.getOriginalPrice());
       product.setImageUrls(dto.getImageUrls());
       product.setQuantity(dto.getQuantity());
       product.setSkuCode(dto.getSkuCode());
       product.setBrand(dto.getBrand());
       return productRepository.save(product);
    }
    public Product getById(Long id) {                 //manage
        return productRepository.findById(id).orElseThrow();
    }

    public Product deleteProduct(long id) {
        Product product = productRepository.findById(id).orElseThrow();
        productRepository.delete(product);
        return product;
    }

}
