package com.onlineshop.onlineshop.Controllers;

import com.onlineshop.onlineshop.Models.CartItem;
import com.onlineshop.onlineshop.Models.DTO.Order.OrderViewDTO;
import com.onlineshop.onlineshop.Models.DTO.ShopCart.CartItemCreateDTO;
import com.onlineshop.onlineshop.Models.DTO.ShopCart.ShoppingCartDTO;
import com.onlineshop.onlineshop.Models.DTO.User.UserDTO;
import com.onlineshop.onlineshop.Models.Product;
import com.onlineshop.onlineshop.Models.ShoppingCart;
import com.onlineshop.onlineshop.Models.User;
import com.onlineshop.onlineshop.Services.ProductService;
import com.onlineshop.onlineshop.Services.ShoppingCartService;
import com.onlineshop.onlineshop.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping(path="/clear")
    public void clear(){

    }

    @DeleteMapping(path="/update")
    public void update(@RequestBody UserDTO userDTO){

    }

    @PostMapping(path="/addToCart")
    public void addToCart(@RequestBody CartItemCreateDTO cartItemCreateDTO){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        ShoppingCart shoppCart = user.getShoppingCart();
        //shoppingCartService.addToCart(shoppCart, new CartItem(cartItemCreateDTO));
        //return new ShoppingCartDTO(newCart);
    }
    @DeleteMapping(path="/removeFromCart/{id}")
    public void removeFromCart(@PathVariable int productId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        ShoppingCart shoppCart = user.getShoppingCart();
        shoppingCartService.removeFromCart(shoppCart, productId);
        //return new ShoppingCartDTO(newCart);
    }

    @PostMapping(path="/reduceProductQuantityInCart/{id}")
    public void reduceProductQuantityInCart(@PathVariable("id") int productId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        ShoppingCart shoppCart = user.getShoppingCart();
        shoppingCartService.reduceProductQuantity(shoppCart, productId);
        //return new ShoppingCartDTO(newCart);
    }

    @PostMapping(path="/increaseProductQuantityInCart/{id}")
    public void increaseProductQuantityInCart(@PathVariable("id") int productId, @RequestParam String username){
        //UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(username);
        ShoppingCart shoppCart = user.getShoppingCart();
        shoppingCartService.increaseProductQuantity(shoppCart, productId);
        //return new ShoppingCartDTO(newCart);
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
