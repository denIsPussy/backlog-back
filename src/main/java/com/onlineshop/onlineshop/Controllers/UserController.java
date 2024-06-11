package com.onlineshop.onlineshop.Controllers;

import com.onlineshop.onlineshop.Models.DTO.Order.OrderViewDTO;
import com.onlineshop.onlineshop.Models.DTO.User.PasswordUpdateDTO;
import com.onlineshop.onlineshop.Models.DTO.ShopCart.ShoppingCartDTO;
import com.onlineshop.onlineshop.Models.DTO.User.UpdateSettingsDTO;
import com.onlineshop.onlineshop.Models.DTO.User.UserViewDTO;
import com.onlineshop.onlineshop.Models.DTO.User.UserUpdateDTO;
import com.onlineshop.onlineshop.Models.Database.User.User;
import com.onlineshop.onlineshop.Models.DTO.Vk.ApiResponse;
import com.onlineshop.onlineshop.Services.ProductService;
import com.onlineshop.onlineshop.Services.ShoppingCartService;
import com.onlineshop.onlineshop.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/changeUserData")
    public ResponseEntity<ApiResponse> changeUserData(@RequestBody UserUpdateDTO userUpdateDTO) throws Exception {
        return ResponseEntity.ok(userService.changeUserData(userUpdateDTO));
    }

    @PostMapping("/changePassword")
    public ResponseEntity<ApiResponse> changePassword(@RequestBody PasswordUpdateDTO passwordUpdateDTO) throws Exception {
        return ResponseEntity.ok(userService.changePassword(passwordUpdateDTO));
    }

    @PostMapping("/settingChildMode")
    public ResponseEntity<ApiResponse> settingChildMode(@RequestBody UpdateSettingsDTO updateSettingsDTO) throws Exception {
        return ResponseEntity.ok(userService.settingChildMode(updateSettingsDTO));
    }

    @PostMapping("/settingTwoFactorAuth")
    public ResponseEntity<ApiResponse> settingTwoFactorAuth(@RequestBody UpdateSettingsDTO updateSettingsDTO) throws Exception {
        return ResponseEntity.ok(userService.settingTwoFactorAuth(updateSettingsDTO));
    }

    @PostMapping("/settingNotifications")
    public ResponseEntity<ApiResponse> settingNotifications(@RequestBody UpdateSettingsDTO updateSettingsDTO) throws Exception {
        return ResponseEntity.ok(userService.settingNotifications(updateSettingsDTO));
    }

    @GetMapping(path = "/byUsername")
    public List<OrderViewDTO> getByUsername(@RequestParam String username) throws Exception {
        return userService.getOrders(username).stream().map(OrderViewDTO::new).toList();
    }

    @GetMapping(path = "/checkingForReview/{productId}")
    public ApiResponse checkingForReview(@PathVariable int productId) throws Exception {
        return userService.checkingForReview(productId);
    }

    @GetMapping(path = "/getShopCart")
    public ShoppingCartDTO getShopCart() throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ShoppingCartDTO(userService.getShopCartByUsername(userDetails.getUsername()));
    }

    @GetMapping(path = "/resetUser")
    public void test() throws Exception {
        List<User> users = userService.getAll();
        for (User user : users){
            if (user.getVkId() != 610502189){
                user.setVkId(0);
                userService.update(user);
            }
        }
    }

    @PostMapping(path = "/topUpDeposit")
    public ApiResponse topUpDeposit(@RequestParam int amount) throws Exception {
        return userService.topUpDeposit(amount);
    }

    @GetMapping(path = "/containsInCart/{productId}")
    public ApiResponse containsInCart(@PathVariable int productId) throws Exception {
        return userService.containsInCart(productId);
    }

    @GetMapping(path = "/deposit")
    public float getDeposit() throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        return user.getDeposit();
    }

    @GetMapping(path = "/getUserInfo")
    public UserViewDTO getUserInfo() throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        return new UserViewDTO(user);
    }
}
