package com.TMDT.api.Api.springboot.service;

import com.TMDT.api.Api.springboot.models.Image;
import com.TMDT.api.Api.springboot.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;

    public void updateImage(List<Image> images) {

    }
}
