package com.TMDT.api.Api.springboot.repositories;

import com.TMDT.api.Api.springboot.models.ProductPhoneCategory;
import com.TMDT.api.Api.springboot.models.ProductPhoneCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductPhoneCategoryRepository extends JpaRepository<ProductPhoneCategory, ProductPhoneCategoryId> {
    ProductPhoneCategory deleteByProduct_Id(int id);
}
