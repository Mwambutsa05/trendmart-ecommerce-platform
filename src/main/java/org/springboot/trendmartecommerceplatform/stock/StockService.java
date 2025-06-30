package org.springboot.trendmartecommerceplatform.stock;

import lombok.AllArgsConstructor;
import org.springboot.trendmartecommerceplatform.Product.Product;
import org.springboot.trendmartecommerceplatform.Product.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class StockService {

    private StockRepository stockRepository;
    private ProductRepository productRepository;

    public Stock addStock(Dto dto, long productId) {
        Product product = productRepository.findById(dto.getProductId()).orElseThrow(() -> new RuntimeException("User not found"));
        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setName(dto.getName());
        stock.setQuantity(dto.getQuantity());
        stock.setDescription(dto.getDescription());
        stock.setCategory(dto.getCategory());
        stock.setCreatedAt(LocalDateTime.now());
        return stockRepository.save(stock);
    }

    public List<Stock> findAllStocks() {
        return stockRepository.findAll();
    }

    public Stock findStockById(Long id) {
        return stockRepository.findById(id).orElseThrow(() -> new RuntimeException("Stock not found"));
    }

    public Product getProductFromStockById(Long productId) {
        Stock stock = stockRepository.findByProductId(productId).orElseThrow(()-> new RuntimeException("product not found"));
        return stock.getProduct();
    }

    public Stock updateStock(Dto dto, long productId) {
        Stock stock = stockRepository.findById(productId).orElseThrow(() -> new RuntimeException("Stock not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("product not found"));
        stock.setProduct(product);
        stock.setName(dto.getName());
        stock.setQuantity(dto.getQuantity());
        stock.setDescription(dto.getDescription());
        stock.setCategory(dto.getCategory());
        stock.setCreatedAt(LocalDateTime.now());
        return stockRepository.save(stock);
    }

    public Stock deleteStock(Long productId) {
        Stock stock = stockRepository.findById(productId).orElseThrow(() -> new RuntimeException("Stock not found"));
        stockRepository.delete(stock);
        return stock;
    }
}
