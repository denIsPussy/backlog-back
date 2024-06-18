package com.onlineshop.onlineshop.Services;

import com.onlineshop.onlineshop.Exceptions.CustomExceptions.InsufficientFundsException;
import com.onlineshop.onlineshop.Exceptions.CustomExceptions.OutOfStockException;
import com.onlineshop.onlineshop.Exceptions.CustomExceptions.ResourceNotFoundException;
import com.onlineshop.onlineshop.Models.DTO.Order.OrderCreateDTO;
import com.onlineshop.onlineshop.Models.DTO.Order.OrderItemCreateDTO;
import com.onlineshop.onlineshop.Models.DTO.Order.OrderViewDTO;
import com.onlineshop.onlineshop.Models.DTO.Vk.ApiResponse;
import com.onlineshop.onlineshop.Models.Database.Order.Order;
import com.onlineshop.onlineshop.Models.Database.Order.OrderItem;
import com.onlineshop.onlineshop.Models.Database.Order.PaymentMethod;
import com.onlineshop.onlineshop.Models.Database.Order.Status;
import com.onlineshop.onlineshop.Models.Database.Product.Product;
import com.onlineshop.onlineshop.Models.Database.ShoppingCart.ShoppingCart;
import com.onlineshop.onlineshop.Models.Database.Store.Store;
import com.onlineshop.onlineshop.Models.Database.Store.StoreItem;
import com.onlineshop.onlineshop.Models.Database.User.User;
import com.onlineshop.onlineshop.Repositories.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private StoreItemRepository storeItemRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private StoreService storeService;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public ApiResponse create(OrderCreateDTO orderCreateDTO) throws Exception {
        Order order = new Order(orderCreateDTO);
        order.setCreationDate(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));

        Status status = statusRepository.findById(1).orElseThrow(() -> new Exception("Не удалось создать заказ"));
        order.setStatus(status);

        order = orderRepository.save(order);

        Store store = storeService.getByCart().stream().filter(item -> item.getId() == orderCreateDTO.getStore().getId()).findFirst().orElseThrow(() -> new Exception("Не удалось создать заказ"));
        logger.info("Найден магазин для покупки");
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemCreateDTO orderItemCreateDTO : orderCreateDTO.getOrderItems()) {
            Product product = productRepository.findById(orderItemCreateDTO.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Продукт с ID=" + orderItemCreateDTO.getProductId() + " не найден"));
            Optional<StoreItem> storeItemOptional = product.getStoreList().stream()
                    .filter(item -> item.getStore().getId() == orderCreateDTO.getStore().getId() && item.getProduct().getId() == orderItemCreateDTO.getProductId())
                    .findFirst();

            if (storeItemOptional.isPresent()) {
                StoreItem storeItem = storeItemOptional.get();
                if (storeItem.getQuantity() >= orderItemCreateDTO.getQuantity()) {
                    storeItem.setQuantity(storeItem.getQuantity() - orderItemCreateDTO.getQuantity());
                    storeItemRepository.save(storeItem);
                } else {
                    throw new OutOfStockException("В выбраном магазине отсутствует данное количество товара: " + product.getName());
                }
            } else {
                throw new ResourceNotFoundException("Товар " + product.getName() + " не найден в выбранном магазине");
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemCreateDTO.getQuantity());
            orderItems.add(orderItem);
        }
        orderItemRepository.saveAll(orderItems);

        order.setOrderItems(orderItems);
        order.setStore(store);
        orderRepository.save(order);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Пользователь с именем " + userDetails.getUsername() + " не найден"));

        PaymentMethod paymentMethod = new PaymentMethod(orderCreateDTO.getPaymentMethod());
        if (paymentMethod.getId() == 2) {
            if (user.getDeposit() >= orderCreateDTO.getTotalAmount()) {
                user.setDeposit(user.getDeposit() - orderCreateDTO.getTotalAmount());
                status = statusRepository.findById(5).orElseThrow();
                order.setStatus(status);
            } else {
                throw new InsufficientFundsException("Пополните баланс для совершения заказа.");
            }
        }
        else{
            status = statusRepository.findById(5).orElseThrow();
            order.setStatus(status);
        }
        orderRepository.save(order);

        user.getOrderList().add(order);

        List<Order> orders = orderRepository.findAll();

        ShoppingCart shoppingCart = user.getShoppingCart();
        cartItemRepository.deleteAll(shoppingCart.getCartItems());
        shoppingCart.setCartItems(new ArrayList<>());

        shoppingCartRepository.save(shoppingCart);
        userRepository.save(user);

        return new ApiResponse(true, "Заказ успешно создан.") {};
    }

    public List<Order> getByUsername(String username) {
        Optional<User> findUser = userRepository.findByUsername(username);
        User user = findUser.orElseThrow(() -> new UsernameNotFoundException("Пользователь с именем " + username + "не найден"));
        return user.getOrderList().stream().sorted((o1, o2) -> o2.getCreationDate().compareTo(o1.getCreationDate())).collect(Collectors.toList());
    }
}
