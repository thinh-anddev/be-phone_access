package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.models.Customer;
import com.TMDT.api.Api.springboot.models.Image;
import com.TMDT.api.Api.springboot.repositories.CustomerRepository;
import com.TMDT.api.Api.springboot.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/images")
public class ImageControllers {
    @Autowired
    ImageRepository imageRepository;

    // get /api/v1/users/getAll
    @GetMapping
    List<Image> get() {
        return imageRepository.findAll();
    }

}
