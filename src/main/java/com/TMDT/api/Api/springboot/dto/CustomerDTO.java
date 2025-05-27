package com.TMDT.api.Api.springboot.dto;

import com.TMDT.api.Api.springboot.models.Address;
import com.TMDT.api.Api.springboot.models.CartDetail;
import com.TMDT.api.Api.springboot.models.Order;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CustomerDTO {
    private int id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private int role;
    private int point;
    private int status;

    private List<AddressDTO> addresses;
    private List<OrderDTO> orders;
    private List<CartDetailDTO> cartDetails;
}
