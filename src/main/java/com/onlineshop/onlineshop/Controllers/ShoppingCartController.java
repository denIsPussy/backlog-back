package com.onlineshop.onlineshop.Controllers;

import com.onlineshop.onlineshop.Models.EverythingElse.CartItem;
import com.onlineshop.onlineshop.Models.DTO.ShopCart.CartItemViewDTO;
import com.onlineshop.onlineshop.Models.DTO.ShopCart.ShoppingCartDTO;
import com.onlineshop.onlineshop.Models.EverythingElse.ShoppingCart;
import com.onlineshop.onlineshop.Models.EverythingElse.User;
import com.onlineshop.onlineshop.Models.Products.Product;
import com.onlineshop.onlineshop.Models.Products.ProductAttribute;
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

    @PostMapping(path="/clear")
    public void clear(){

    }
    @PostMapping(path="/addToCart")
    public ShoppingCartDTO addToCart(@RequestBody CartItemViewDTO cartItemViewDTO){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        ShoppingCart shoppCart = user.getShoppingCart();
        Product product = productService.getById(cartItemViewDTO.getProduct().getId());
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCart(shoppCart);
        cartItem.setQuantity(cartItemViewDTO.getQuantity());
        logger.info("CONTROLLER Quantity of the product in the cart item: {}", cartItem.getQuantity());
        logger.info("CONTROLLER Quantity in the DTO: {}", cartItemViewDTO.getQuantity());
        ShoppingCart newCart = shoppingCartService.addToCart(cartItem);
        return new ShoppingCartDTO(newCart);
    }

    @DeleteMapping(path="/removeFromCart/{productId}")
    public ShoppingCartDTO removeFromCart(@PathVariable int productId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        ShoppingCart shoppCart = user.getShoppingCart();
        ShoppingCart newCart = shoppingCartService.removeFromCart(shoppCart.getId(), productId);
        return new ShoppingCartDTO(newCart);
    }

    @PostMapping(path="/reduceProductQuantityInCart/{productId}")
    public ShoppingCartDTO reduceProductQuantityInCart(@PathVariable int productId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        ShoppingCart shoppCart = user.getShoppingCart();
        return new ShoppingCartDTO(shoppingCartService.reduceProductQuantity(shoppCart, productId));
        //return new ShoppingCartDTO(newCart);
    }

    @PostMapping(path="/increaseProductQuantityInCart/{productId}")
    public ShoppingCartDTO increaseProductQuantityInCart(@PathVariable int productId){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        ShoppingCart shoppCart = user.getShoppingCart();
        return new ShoppingCartDTO(shoppingCartService.increaseProductQuantity(shoppCart, productId));
        //return new ShoppingCartDTO(newCart);
    }

    @GetMapping(path="/byUser/{id}")
    public ShoppingCartDTO getByUserId(@PathVariable int userId){
        return null;
    }
}
