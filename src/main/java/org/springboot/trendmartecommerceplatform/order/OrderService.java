package org.springboot.trendmartecommerceplatform.order;

import lombok.RequiredArgsConstructor;
import org.springboot.trendmartecommerceplatform.order.OrderRequest;
import org.springboot.trendmartecommerceplatform.order.OrderItemRequest;
import org.springboot.trendmartecommerceplatform.Product.Product;
import org.springboot.trendmartecommerceplatform.Product.ProductRepository;
import org.springboot.trendmartecommerceplatform.user.User;
import org.springboot.trendmartecommerceplatform.user.UserRepository;
import org.springboot.trendmartecommerceplatform.address.Address;
import org.springboot.trendmartecommerceplatform.address.AddressRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final OrderItemRepository itemRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private final AddressRepository addressRepo;


    public ResponseEntity<String> placeOrder(OrderRequest request) {
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepo.findById(request.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest itemReq : request.getItems()) {
            Product product = productRepo.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);

            OrderItem item = OrderItem.builder()
                    .productId(product.getId())
                    .quantity(itemReq.getQuantity())
                    .price(product.getPrice())
                    .build();

            orderItems.add(item);
        }

        Order order = Order.builder()
                .user(user)
                .address(address)
                .paymentMethod(request.getPaymentMethod())
                .status("PENDING")
                .totalAmount(totalAmount)
                .createdAt(LocalDateTime.now())
                .build();

        order = orderRepo.save(order);

        for (OrderItem item : orderItems) {
            item.setOrder(order);
        }

        itemRepo.saveAll(orderItems);

        return ResponseEntity.ok("Order placed successfully!");
    }
    public ResponseEntity<List<Order>> getOrdersByUser(Long userId) {
        List<Order> orders = orderRepo.findByUserId(userId);
        return ResponseEntity.ok(orders);
    }

}
