package com.TMDT.api.Api.springboot.repositories;

import com.TMDT.api.Api.springboot.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    int deleteByProductId(int productId);
}
