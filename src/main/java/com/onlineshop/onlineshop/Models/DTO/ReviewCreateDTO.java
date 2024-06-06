package com.onlineshop.onlineshop.Models.DTO;

import com.onlineshop.onlineshop.Models.DTO.User.UserNestedDTO;
import com.onlineshop.onlineshop.Models.Products.Review;

import java.time.LocalDateTime;

public class ReviewCreateDTO {
    private int productId;
    private String header;
    private String content;
    private float rating;

    public ReviewCreateDTO() {
    }

    public ReviewCreateDTO(Review review) {
        this.productId = review.getProduct().getId();
        this.header = review.getHeader();
        this.content = review.getContent();
        this.rating = review.getRating();
    }

    public int getProductId() {
        return productId;
    }

    public String getHeader() {
        return header;
    }

    public String getContent() {
        return content;
    }

    public float getRating() {
        return rating;
    }
}
