package com.onlineshop.onlineshop.Controllers;

import com.onlineshop.onlineshop.Models.DTO.Order.OrderCreateDTO;
import com.onlineshop.onlineshop.Models.DTO.Order.OrderViewDTO;
import com.onlineshop.onlineshop.Models.DTO.OrderItem.OrderItemCreateDTO;
import com.onlineshop.onlineshop.Models.DTO.PaymentMethodCompositeDTO;
import com.onlineshop.onlineshop.Models.DTO.ShippingMethodCompositeDTO;
import com.onlineshop.onlineshop.Models.EverythingElse.Order;
import com.onlineshop.onlineshop.Models.EverythingElse.OrderItem;
import com.onlineshop.onlineshop.Models.EverythingElse.Status;
import com.onlineshop.onlineshop.Models.EverythingElse.User;
import com.onlineshop.onlineshop.Models.Products.Product;
import com.onlineshop.onlineshop.Repositories.PaymentMethodRepository;
import com.onlineshop.onlineshop.Repositories.ShippingMethodRepository;
import com.onlineshop.onlineshop.Services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private ShippingMethodRepository shippingMethodRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @GetMapping(path="/{id}")
    public OrderViewDTO getById(@PathVariable int id){
        return null;
    }

    @GetMapping(path="/getPaymentMethods")
    public List<PaymentMethodCompositeDTO> getPaymentMethods(){
        return paymentMethodRepository.findAll().stream().map(PaymentMethodCompositeDTO::new).toList();
    }

    @GetMapping(path="/getShippingMethods")
    public List<ShippingMethodCompositeDTO> getShippingMethods(){
        return shippingMethodRepository.findAll().stream().map(ShippingMethodCompositeDTO::new).toList();
    }

    @GetMapping(path="/byUser")
    public List<OrderViewDTO> getByUsername(@RequestParam String username){
        List<Order> orders = orderService.getByUsername(username);
        return orders.stream().map(OrderViewDTO::new).toList();
    }

    @PostMapping(path="/create")
    public List<OrderViewDTO> create(@RequestBody OrderCreateDTO orderCreateDTO){
        return orderService.create(orderCreateDTO);
    }
}
