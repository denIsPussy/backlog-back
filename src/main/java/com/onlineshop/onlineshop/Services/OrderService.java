package com.onlineshop.onlineshop.Services;

import com.onlineshop.onlineshop.Models.DTO.Order.OrderCreateDTO;
import com.onlineshop.onlineshop.Models.DTO.Order.OrderViewDTO;
import com.onlineshop.onlineshop.Models.DTO.OrderItem.OrderItemCreateDTO;
import com.onlineshop.onlineshop.Models.EverythingElse.*;
import com.onlineshop.onlineshop.Models.Products.Product;
import com.onlineshop.onlineshop.Repositories.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderViewDTO> create(OrderCreateDTO orderCreateDTO){
        Order order = new Order(orderCreateDTO);
        order.setCreationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));

        Status status = statusRepository.findById(1).orElseThrow();
        order.setStatus(status);

        order = orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemCreateDTO orderItemCreateDTO: orderCreateDTO.getOrderItems()){
            //logger.info("ALL OK");
            Product product = productRepository.findById(orderItemCreateDTO.getProductId()).orElseThrow();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemCreateDTO.getQuantity());
            orderItems.add(orderItem);
        }
        orderItemRepository.saveAll(orderItems);

        order.setOrderItems(orderItems);
        orderRepository.save(order);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        PaymentMethod paymentMethod = new PaymentMethod(orderCreateDTO.getPaymentMethod());
        if (paymentMethod.getId() == 2){
            if (user.getDeposit() >= orderCreateDTO.getTotalAmount()) {
                user.setDeposit(user.getDeposit() - orderCreateDTO.getTotalAmount());
                status = statusRepository.findById(5).orElseThrow();
                order.setStatus(status);
            }
            else{
                status = statusRepository.findById(2).orElseThrow();
                order.setStatus(status);
            }
        }
        orderRepository.save(order);

        user.getOrderList().add(order);
        userRepository.save(user);

        List<Order> orders = orderRepository.findAll();

        return orderRepository.findAll().stream().map(OrderViewDTO::new).toList();
    }

    public Order getById(int id){
        return null;
    }

    public List<Order> getAll(){
        return orderRepository.findAll();
    }

    public Status getStatusById(int statusId){
        return statusRepository.findById(statusId).orElseThrow();
    }

    public List<Order> getByUsername(String username){
        Optional<User> findUser = userRepository.findByUsername(username);
        User user = findUser.orElseThrow();
        return user.getOrderList();
    }
}
