package com.onlineshop.onlineshop.Models;

import com.onlineshop.onlineshop.Models.DTO.StatusDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
@Entity
@Table(name = "statuses")
public class Status {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    @NotNull
    private String description;

    public Status(){

    }

    public Status(StatusDTO statusDTO) {
        this.id = statusDTO.getId();
        this.description = statusDTO.getDescription();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
