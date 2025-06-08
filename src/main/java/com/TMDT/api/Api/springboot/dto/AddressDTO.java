package com.TMDT.api.Api.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
