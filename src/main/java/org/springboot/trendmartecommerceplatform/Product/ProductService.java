package org.springboot.trendmartecommerceplatform.Product;

import lombok.AllArgsConstructor;
import org.springboot.trendmartecommerceplatform.discountAds.Discount;
import org.springboot.trendmartecommerceplatform.discountAds.DiscountRepository;
import org.springboot.trendmartecommerceplatform.exceptionHandling.ResourceNotFound;
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

    public Product updateProduct(long id, Dto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Product not found"));

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

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Product not found"));
    }

    public Product deleteProduct(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Product not found"));
        productRepository.delete(product);
        return product;
    }
}
