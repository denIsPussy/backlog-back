package com.onlineshop.onlineshop.Models;

import com.onlineshop.onlineshop.Models.DTO.StoreDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stores")
public class Store {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotNull
    private String name;

    @JoinColumn(name = "address_id")
    @OneToOne
    private Address address;

    @OneToMany(mappedBy = "store")
    private List<StoreItem> storeItems;

    public Store(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<StoreItem> getStoreItems() {
        return storeItems;
    }

    public void setStoreItems(List<StoreItem> storeItems) {
        this.storeItems = storeItems;
    }
}
