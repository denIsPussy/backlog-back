package com.onlineshop.onlineshop.Controllers;

import com.onlineshop.onlineshop.Models.DTO.Order.OrderCreateDTO;
import com.onlineshop.onlineshop.Models.DTO.Order.OrderViewDTO;
import com.onlineshop.onlineshop.Models.DTO.Order.PaymentMethodCompositeDTO;
import com.onlineshop.onlineshop.Models.DTO.Order.ShippingMethodCompositeDTO;
import com.onlineshop.onlineshop.Models.DTO.Store.StoreNestedDTO;
import com.onlineshop.onlineshop.Models.DTO.Vk.ApiResponse;
import com.onlineshop.onlineshop.Models.Database.Order.Order;
import com.onlineshop.onlineshop.Repositories.PaymentMethodRepository;
import com.onlineshop.onlineshop.Repositories.ShippingMethodRepository;
import com.onlineshop.onlineshop.Services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private ShippingMethodRepository shippingMethodRepository;
    @Autowired
    private StoreService storeService;

    @GetMapping(path = "/getPaymentMethods")
    public List<PaymentMethodCompositeDTO> getPaymentMethods() {
        return paymentMethodRepository.findAll().stream().map(PaymentMethodCompositeDTO::new).toList();
    }

    @GetMapping(path = "/getShippingMethods")
    public List<ShippingMethodCompositeDTO> getShippingMethods() {
        return shippingMethodRepository.findAll().stream().map(ShippingMethodCompositeDTO::new).toList();
    }

    @GetMapping(path = "/getStore")
    public List<StoreNestedDTO> getStore() throws Exception {
        return storeService.getByCart().stream().map(StoreNestedDTO::new).toList();
    }

    @GetMapping(path = "/byUser")
    public List<OrderViewDTO> getByUsername(@RequestParam String username) {
        List<Order> orders = orderService.getByUsername(username);
        return orders.stream().map(OrderViewDTO::new).toList();
    }

    @PostMapping(path = "/create")
    public ApiResponse create(@RequestBody OrderCreateDTO orderCreateDTO) throws Exception {
        return orderService.create(orderCreateDTO);
    }
}
