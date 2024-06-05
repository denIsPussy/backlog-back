package com.onlineshop.onlineshop.Services;

import com.onlineshop.onlineshop.Models.EverythingElse.Order;
import com.onlineshop.onlineshop.Models.EverythingElse.User;
import com.onlineshop.onlineshop.Repositories.OrderRepository;
import com.onlineshop.onlineshop.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void create(Order order){
        return;
    }

    public Order getById(int id){
        return null;
    }

    public List<Order> getByUsername(String username){
        Optional<User> findUser = userRepository.findByUsername(username);
        User user = findUser.orElseThrow();
        return user.getOrderList();
    }
}
