package com.TMDT.api.Api.springboot.dto;

import com.TMDT.api.Api.springboot.models.Customer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

public class AddressDTO {
    private int id;
    private int provinceId;
    private String provinceValue;
    private int districtId;
    private String districtValue;
    private int wardId;
    private String wardValue;
    private String subAddress;
    private int status;
}
