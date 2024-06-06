package com.onlineshop.onlineshop.Controllers;

import com.onlineshop.onlineshop.Models.DTO.Order.OrderViewDTO;
import com.onlineshop.onlineshop.Models.DTO.ShopCart.CartItemCreateDTO;
import com.onlineshop.onlineshop.Models.DTO.ShopCart.ShoppingCartDTO;
import com.onlineshop.onlineshop.Models.DTO.User.UserDTO;
import com.onlineshop.onlineshop.Models.EverythingElse.ShoppingCart;
import com.onlineshop.onlineshop.Models.EverythingElse.User;
import com.onlineshop.onlineshop.Services.ProductService;
import com.onlineshop.onlineshop.Services.ShoppingCartService;
import com.onlineshop.onlineshop.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
//    @Autowired
//    private EmailService emailService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping(path="/byUsername")
    public List<OrderViewDTO> getByUsername(@RequestParam String username){
        return userService.getOrders(username).stream().map(OrderViewDTO::new).toList();
    }

    @GetMapping(path="/getShopCart")
    public ShoppingCartDTO getShopCart(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ShoppingCartDTO(userService.getShopCartByUsername(userDetails.getUsername()));
    }

    @DeleteMapping(path="/update")
    public void update(@RequestBody UserDTO userDTO){

    }

//    @PostMapping(path="/email")
//    public void sendEmail(@RequestParam String emailTo, @RequestParam String text){
//        emailService.sendSimpleMessage(emailTo, "Your 2FA Code", text);
//    }

    @GetMapping(path="/setChildMode")
    public UserDTO setChildMode(@RequestParam boolean isEnabled) {
        return null;
    }

    @GetMapping(path="/configureNotifications")
    public UserDTO configureNotifications(@RequestParam boolean isEnabled){
        return null;
    }
}
