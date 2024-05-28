package com.onlineshop.onlineshop.Models.DTO;

import com.onlineshop.onlineshop.Models.Status;
import com.onlineshop.onlineshop.Models.Status;

public class StatusDTO {
    private int id;
    private String description;

    public StatusDTO() {

    }

    public StatusDTO(Status status) {
        this.id = status.getId();
        this.description = status.getDescription();
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
