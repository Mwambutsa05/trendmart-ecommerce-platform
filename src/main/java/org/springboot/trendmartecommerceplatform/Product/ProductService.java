package org.springboot.trendmartecommerceplatform.Product;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springboot.trendmartecommerceplatform.discountAds.Discount;
import org.springboot.trendmartecommerceplatform.discountAds.DiscountDto;
import org.springboot.trendmartecommerceplatform.discountAds.DiscountRepository;
import org.springframework.stereotype.Service;

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
        productToAdd.setImageUrl(dto.getImageUrl());
        productToAdd.setDiscount(discount);// to put discount
//        save product
        return productRepository.save(productToAdd);
    }
    public Product updateProduct(long id, Dto dto) {          //Edit
       Product product = productRepository.findById(id).orElseThrow();
       product.setName(dto.getName());
       product.setDescription(dto.getDescription());
       product.setPrice(dto.getPrice());
       product.setImageUrl(dto.getImageUrl());
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
