package com.onlineshop.onlineshop.Services;

import com.onlineshop.onlineshop.Models.EverythingElse.CartItem;
import com.onlineshop.onlineshop.Models.EverythingElse.Notification;
import com.onlineshop.onlineshop.Models.EverythingElse.OrderItem;
import com.onlineshop.onlineshop.Models.EverythingElse.User;
import com.onlineshop.onlineshop.Repositories.CartItemRepository;
import com.onlineshop.onlineshop.Repositories.NotificationRepository;
import com.onlineshop.onlineshop.Repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItem> createAll(List<OrderItem> cartItems){
        return orderItemRepository.saveAll(cartItems);
    }

    public void delete(int id){
        return;
    }

    public void update(OrderItem cartItem){
        return;
    }

    public OrderItem getById(int id){
        return null;
    }

    public List<OrderItem> getByUserId(int userId){
        return null;
    }
}
