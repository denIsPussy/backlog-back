package com.onlineshop.onlineshop.Services;

import com.onlineshop.onlineshop.Exceptions.CustomExceptions.ResourceNotFoundException;
import com.onlineshop.onlineshop.Models.EverythingElse.Category;
import com.onlineshop.onlineshop.Models.Products.Product;
import com.onlineshop.onlineshop.Models.Products.Review;
import com.onlineshop.onlineshop.Repositories.CategoryRepository;
import com.onlineshop.onlineshop.Repositories.ProductRepository;
import com.onlineshop.onlineshop.Repositories.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public void updateProductRating(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

        double averageRating = product.getReviewList().stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0); // Возвращает 0.0, если отзывов нет

        product.setRating((float) averageRating);
        productRepository.save(product);
    }

    public List<Review> createReview(Review review) {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        review.setCreatedAt(now);
        reviewRepository.save(review);
        reviewRepository.flush();
        updateProductRating(review.getProduct().getId());
        return reviewRepository.findAll();
    }

    public List<Review> deleteReview(int reviewId) throws ResourceNotFoundException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id " + reviewId));
        int productId = review.getProduct().getId();
        reviewRepository.delete(review);
        reviewRepository.flush();
        updateProductRating(productId);
        return reviewRepository.findAll();
    }

    public List<Review> updateReview(Review updReview) throws ResourceNotFoundException {
        Review review = reviewRepository.findById(updReview.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id " + updReview.getId()));
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        review.setUpdatedAt(now);
        review.setHeader(updReview.getHeader());
        review.setContent(updReview.getContent());
        review.setRating(updReview.getRating());
        reviewRepository.save(review);
        reviewRepository.flush();
        updateProductRating(review.getProduct().getId());
        return reviewRepository.findAll();
    }

    public List<Review> getReviewsByProductId(int productId) {
        return reviewRepository.findByProductId(productId);
    }

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<Product> filterByRating(String rating){
        return null;
    }
    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }
    public List<Product> filterByPrice(String price){
        return null;
    }
    public Page<Product> filterByCategory(int categoryId, Pageable pageable){
        return productRepository.findByCategory_Id(categoryId, pageable);
    }
    public List<Product> search(String name){
        return null;
    }
    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    public Product getById(int productId){
        return productRepository.findById(productId).orElseThrow();
    }
    public void test(){

        try {

            List<Product> products = productRepository.findAll();
            for (int i = 0; i < products.size(); i++){
                Product product = products.get(i);
                String imagePath = "";
                switch (product.getId()){
                    case 1:
                        imagePath = "images/Gainward GeForce RTX 4090 Phantom GS.png";
                        break;
                    case 2:
                        imagePath = "images/DEEPCOOL LS720 WH.png";
                        break;
                    case 3:
                        imagePath = "images/Intel Core i9-14900K.png";
                        break;
                    case 4:
                        imagePath = "images/MSI Katana B12VFK-463XRU.png";
                        break;
                    case 5:
                        imagePath = "images/Razer Viper Ultimate.png";
                        break;
                }
                ClassPathResource imageResource = new ClassPathResource(imagePath);
                Path path = imageResource.getFile().toPath();
                byte[] imageBytes = Files.readAllBytes(path);
                product.setImage(imageBytes);
                productRepository.save(product);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
