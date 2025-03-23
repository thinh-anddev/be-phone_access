package com.TMDT.api.Api.springboot.dto;

import com.TMDT.api.Api.springboot.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListProductDTO {
    int totalPage;
    List<Product> products;

}
