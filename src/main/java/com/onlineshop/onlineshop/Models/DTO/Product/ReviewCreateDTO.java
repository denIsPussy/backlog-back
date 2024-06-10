package com.onlineshop.onlineshop.Models.DTO.Product;

import com.onlineshop.onlineshop.Models.Database.Product.Review;

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
