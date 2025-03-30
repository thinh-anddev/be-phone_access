package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.models.PhoneCategory;
import com.TMDT.api.Api.springboot.repositories.PhoneCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/phoneCategories")
public class PhoneCategoryControllers {

    @Autowired
    private PhoneCategoryRepository phoneCategoryRepository;
    @GetMapping
    public ResponseEntity<List<PhoneCategory>> getAll(){
        return new ResponseEntity<>(phoneCategoryRepository.findAll(), HttpStatus.OK) ;
    }

//    public List<PhoneCategory> getPhoneCategoryByProduct(int product_id){
//        return phoneCategoryRepository.findById(product_id);
//    }
//    đang xem thử mqh many to many của phoneCategory - product có OK không
}
