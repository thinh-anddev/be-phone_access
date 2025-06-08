package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.dto.CategoryDTO;
import com.TMDT.api.Api.springboot.mapper.CategoryMapper;
import com.TMDT.api.Api.springboot.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/categories")
public class CategoryControllers {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<ResponseObject> getAll() {
        List<CategoryDTO> categories = categoryMapper.toListDto(categoryService.getAll());
        return ResponseEntity.ok(new ResponseObject("ok", "Success", categories));
    }
}
