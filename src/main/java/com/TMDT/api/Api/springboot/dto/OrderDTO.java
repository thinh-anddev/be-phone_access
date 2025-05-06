package com.TMDT.api.Api.springboot.dto;

import com.TMDT.api.Api.springboot.models.Customer;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private int id;
    private Customer customer;
    private List<OrderDetailDTO> orderDetails;
    private String address;
    private int discount;
    private int total;
    private int paymentStatus;
    private LocalDateTime paymentDate;
    private String deliveryId;
    private LocalDateTime createDate;
    private int status;
}
