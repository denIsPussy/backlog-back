package com.onlineshop.onlineshop.Controllers;

import com.onlineshop.onlineshop.Exceptions.CustomExceptions.ResourceNotFoundException;
import com.onlineshop.onlineshop.Models.DTO.Product.CategoryCompositeDTO;
import com.onlineshop.onlineshop.Models.DTO.Product.ProductViewDTO;
import com.onlineshop.onlineshop.Models.DTO.Product.ReviewCreateDTO;
import com.onlineshop.onlineshop.Models.DTO.Product.ReviewDTO;
import com.onlineshop.onlineshop.Models.Database.User.User;
import com.onlineshop.onlineshop.Models.Database.Product.Product;
import com.onlineshop.onlineshop.Models.Database.Product.Review;
import com.onlineshop.onlineshop.Services.ProductService;
import com.onlineshop.onlineshop.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @GetMapping("/{productId}/reviews")
    public List<ReviewDTO> getReviewsByProductId(@PathVariable int productId) {
        return productService.getReviewsByProductId(productId).stream().map(ReviewDTO::new).toList();
    }

    @GetMapping(path = "/byRating")
    public List<ProductViewDTO> filterByRating(@RequestParam String rating) {
        return null;
    }

    @GetMapping(path = "/byPrice")
    public List<ProductViewDTO> filterByPrice(@RequestParam String price) {
        return null;
    }

    @GetMapping(path = "/byCategory/{categoryId}")
    public Page<ProductViewDTO> filterByCategory(@PathVariable int categoryId,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.filterByCategory(categoryId, pageable).map(ProductViewDTO::new);
    }

    @GetMapping(path = "/categories")
    public List<CategoryCompositeDTO> getCategories() {
        return productService.getCategories().stream().map(CategoryCompositeDTO::new).toList();
    }

    @GetMapping(path = "/search")
    public List<ProductViewDTO> search(@RequestParam String name) {
        return null;
    }

    @GetMapping(path = "/get/{productId}")
    public ProductViewDTO getById(@PathVariable int productId) {
        return new ProductViewDTO(productService.getById(productId));
    }

    @PostMapping("/createReview")
    public List<ReviewDTO> createReview(@RequestBody ReviewCreateDTO reviewDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());
        Review newReview = new Review(reviewDTO);
        Product product = productService.getById(reviewDTO.getProductId());
        newReview.setProduct(product);
        newReview.setUser(user);
        return productService.createReview(newReview).stream().map(ReviewDTO::new).toList();
    }

    @PutMapping("/updateReview")
    public List<ReviewDTO> updateReview(@RequestBody ReviewDTO reviewDTO) throws ResourceNotFoundException {
        return productService.updateReview(new Review(reviewDTO)).stream().map(ReviewDTO::new).toList();
    }

    @DeleteMapping("/deleteReview/{reviewId}")
    public List<ReviewDTO> deleteReview(@PathVariable int reviewId) throws ResourceNotFoundException {
        return productService.deleteReview(reviewId).stream().map(ReviewDTO::new).toList();
    }

    @GetMapping(path = "/test")
    public void test() {
        productService.test();
    }

    @GetMapping(path = "/testCreateReviews")
    public void testCreateReviews() {
        User user = userService.getByUsername("movavi");
        User user2 = userService.getByUsername("denis");
        Product product = productService.getById(1);

        Review review1 = new Review();
        review1.setHeader("Невероятная производительность для геймеров");
        review1.setContent("Эта видеокарта изменила мои впечатления от игр. Теперь я могу играть в самые современные игры на ультра настройках без каких-либо подвисаний или задержек. RTX 4090 действительно стоит своих денег!");
        review1.setRating(4.8f);
        review1.setUpdatedAt(null); // Так как отзыв не обновлялся
        review1.setUser(user);
        review1.setProduct(product);

        Review review2 = new Review();
        review2.setHeader("Высокая цена, но оправданное качество");
        review2.setContent("Изначально я сомневался из-за высокой стоимости, но после нескольких месяцев использования могу сказать — видеокарта стоит каждого рубля. Идеально подходит для игр, видеомонтажа и VR.");
        review2.setRating(4.7f);
        review2.setUpdatedAt(null); // Так как отзыв не обновлялся
        review2.setUser(user2);
        review2.setProduct(product);

        productService.createReview(review1);
        productService.createReview(review2);

    }
}
