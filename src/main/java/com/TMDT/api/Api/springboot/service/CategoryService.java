package com.TMDT.api.Api.springboot.service;

import com.TMDT.api.Api.springboot.models.Category;
import com.TMDT.api.Api.springboot.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAll() {
        List<Category> result = categoryRepository.findAll();
        for (Category category : result) {
            category.setProducts(null);
        }
        return result;
    }

    public Category getById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
