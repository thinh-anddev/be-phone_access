package com.TMDT.api.Api.springboot.dto;

import com.TMDT.api.Api.springboot.models.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private int id;
    private CustomerDTO customer;
    private List<OrderDetailDTO> orderDetails;
    private String address;
    private int discount;
    private int total;
    private int paymentStatus;
    private LocalDateTime paymentDate;
    private String deliveryId;
    private LocalDateTime createDate;
    private String note;
    private int shippingFee;
    private int status;
}
