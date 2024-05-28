package com.onlineshop.onlineshop.Controllers;

import com.onlineshop.onlineshop.Models.DTO.Order.OrderViewDTO;
import com.onlineshop.onlineshop.Models.Order;
import com.onlineshop.onlineshop.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping(path="/create")
    public void create(@RequestBody OrderViewDTO orderViewDTO){

    }
    @GetMapping(path="/{id}")
    public OrderViewDTO getById(@PathVariable int id){
        return null;
    }
    @GetMapping(path="/byUser/{id}")
    public List<OrderViewDTO> getByUsername(@RequestParam String username){
        List<Order> orders = orderService.getByUsername(username);
        return orders.stream().map(OrderViewDTO::new).toList();
    }
}
