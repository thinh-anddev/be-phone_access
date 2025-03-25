package com.TMDT.api.Api.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReqOrderDTO {
    List<Integer> cartDetailIds;
    int customerId;
    String deliveryId;
    int paymentStatus;
    String address;
    String note;
    int shippingFee;
    int point;
}
