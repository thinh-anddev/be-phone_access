package com.TMDT.api.Api.springboot.dto;


import lombok.*;

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
