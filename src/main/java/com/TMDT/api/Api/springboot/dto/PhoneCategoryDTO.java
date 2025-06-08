package com.TMDT.api.Api.springboot.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneCategoryDTO {
    private int id;
    private String name;
    private int status;
}
