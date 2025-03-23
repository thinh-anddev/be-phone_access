package com.TMDT.api.Api.springboot.models;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto generate id
    private int id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private int role;
    private int point;
    private int status;


    @OneToMany(mappedBy = "customer")
    @JsonManagedReference(value = "customer-address")
    private List<Address> addresses;

    @OneToMany(mappedBy = "customer")
    @JsonBackReference
    private List<Order> orders;


    @OneToMany(mappedBy = "customer")
    private List<CartDetail> cartDetails;

}
