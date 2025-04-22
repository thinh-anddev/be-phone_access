package com.TMDT.api.Api.springboot.dto;

import com.TMDT.api.Api.springboot.models.Order;
import com.TMDT.api.Api.springboot.models.PhoneCategory;
import com.TMDT.api.Api.springboot.models.Product;
import jakarta.persistence.*;

public class OrderDetailDTO {
    private int id;
    private Order order;
    private Product product;
    private PhoneCategory phoneCategory;
    private int quantity;
    private int price;
    private int status;
}
