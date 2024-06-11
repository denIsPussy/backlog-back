package com.onlineshop.onlineshop.Controllers;

import com.onlineshop.onlineshop.Models.DTO.Vk.ApiResponse;
import com.onlineshop.onlineshop.Models.Database.ShoppingCart.CartItem;
import com.onlineshop.onlineshop.Models.DTO.ShopCart.CartItemViewDTO;
import com.onlineshop.onlineshop.Models.DTO.ShopCart.ShoppingCartDTO;
import com.onlineshop.onlineshop.Models.Database.ShoppingCart.ShoppingCart;
import com.onlineshop.onlineshop.Models.Database.User.User;
import com.onlineshop.onlineshop.Models.Database.Product.Product;
import com.onlineshop.onlineshop.Services.AuthService;
import com.onlineshop.onlineshop.Services.ProductService;
import com.onlineshop.onlineshop.Services.ShoppingCartService;
import com.onlineshop.onlineshop.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @PostMapping(path="/addToCart")
    public ApiResponse addToCart(@RequestBody CartItemViewDTO cartItemViewDTO) throws Exception {
        return shoppingCartService.addToCart(cartItemViewDTO);
    }

    @DeleteMapping(path="/removeFromCart/{productId}")
    public ApiResponse removeFromCart(@PathVariable int productId) throws Exception {
        return shoppingCartService.removeFromCart(productId);
    }

    @PostMapping(path="/reduceProductQuantityInCart/{productId}")
    public ApiResponse reduceProductQuantityInCart(@PathVariable int productId) throws Exception {
        return shoppingCartService.reduceProductQuantity(productId);
    }

    @PostMapping(path="/increaseProductQuantityInCart/{productId}")
    public ApiResponse increaseProductQuantityInCart(@PathVariable int productId) throws Exception {
        return shoppingCartService.increaseProductQuantity(productId);
    }
}
