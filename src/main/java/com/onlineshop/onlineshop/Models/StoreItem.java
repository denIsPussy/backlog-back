package com.onlineshop.onlineshop.Models;

import com.onlineshop.onlineshop.Models.DTO.StoreItem.StoreItemNestedDTO;
import com.onlineshop.onlineshop.Models.DTO.StoreItem.StoreItemViewDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
@Entity
@Table(name = "storeItems")
public class StoreItem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantity")
    @NotNull
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    public StoreItem(){

    }

    public StoreItem(StoreItemViewDTO storeItemViewDTO) {
        this.id = storeItemViewDTO.getId();
        this.quantity = storeItemViewDTO.getQuantity();
        this.product = new Product(storeItemViewDTO.getProductNestedDTO());
    }

    public StoreItem(StoreItemNestedDTO storeItemNestedDTO) {
        this.id = storeItemNestedDTO.getId();
        this.quantity = storeItemNestedDTO.getQuantity();
        this.store = new Store(storeItemNestedDTO.getStoreNestedDTO());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
