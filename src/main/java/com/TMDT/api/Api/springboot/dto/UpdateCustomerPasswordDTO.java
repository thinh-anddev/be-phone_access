package com.TMDT.api.Api.springboot.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerPasswordDTO {
    private int id;
    private String password;
    private String newPassword;
}
