package com.onlineshop.onlineshop.Services;

import com.onlineshop.onlineshop.Models.Product;
import com.onlineshop.onlineshop.Repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<Product>  filterByRating(String rating){
        return null;
    }
    public List<Product> filterByPrice(String price){
        return null;
    }
    public List<Product> filterByCategory(String category){
        return null;
    }
    public List<Product> search(String name){
        return null;
    }
    public List<Product> getAll(){
        return productRepository.findAll();
    }
    public Optional<Product> getById(int productId){
        return productRepository.findById(productId);
    }
    public void test(){
        ClassPathResource imageResource = new ClassPathResource("images/word.png");
        Path path = null;
        try {
            path = imageResource.getFile().toPath();
            byte[] imageBytes = Files.readAllBytes(path);
            List<Product> products = productRepository.findAll();
            for (int i = 0; i < products.size(); i++){
                Product product = products.get(i);
                product.setImage(imageBytes);
                productRepository.save(product);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
