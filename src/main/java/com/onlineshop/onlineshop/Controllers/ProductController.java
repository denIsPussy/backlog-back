package com.onlineshop.onlineshop.Controllers;

import com.onlineshop.onlineshop.Models.DTO.CategoryCompositeDTO;
import com.onlineshop.onlineshop.Models.DTO.Product.ProductViewDTO;
import com.onlineshop.onlineshop.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping(path="/byCategory/{categoryId}")
    public Page<ProductViewDTO> filterByCategory(@PathVariable int categoryId,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return productService.filterByCategory(categoryId, pageable).map(ProductViewDTO::new);
    }
    @GetMapping(path="/categories")
    public List<CategoryCompositeDTO> getCategories(){
        return productService.getCategories().stream().map(CategoryCompositeDTO::new).toList();
    }
    @GetMapping(path="/search")
    public List<ProductViewDTO> search(@RequestParam String name){
        return null;
    }
    @GetMapping(path = "/")
    public Page<ProductViewDTO> getAll(@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.getAll(pageable).map(ProductViewDTO::new);
    }

    @GetMapping(path="/test")
    public void test(){
        productService.test();
    }
}
