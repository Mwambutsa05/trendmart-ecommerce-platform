package org.springboot.trendmartecommerceplatform.Product;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }
    public Product addProduct(Product product) {
        return productRepository.save(product);
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
