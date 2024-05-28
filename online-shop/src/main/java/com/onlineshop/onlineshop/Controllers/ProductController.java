package com.onlineshop.onlineshop.Controllers;

import com.onlineshop.onlineshop.Models.DTO.Product.ProductViewDTO;
import com.onlineshop.onlineshop.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(path="/byRating")
    public List<ProductViewDTO> filterByRating(@RequestParam String rating){
        return null;
    }
    @GetMapping(path="/byPrice")
    public List<ProductViewDTO> filterByPrice(@RequestParam String price){
        return null;
    }
    @GetMapping(path="/byCategory")
    public List<ProductViewDTO> filterByCategory(@RequestParam String category){
        return null;
    }
    @GetMapping(path="/search")
    public List<ProductViewDTO> search(@RequestParam String name){
        return null;
    }
    @GetMapping(path="/")
    public List<ProductViewDTO> getAll(){
        return productService.getAll().stream().map(ProductViewDTO::new).toList();
    }

    @GetMapping(path="/test")
    public void test(){
        productService.test();
    }
}
