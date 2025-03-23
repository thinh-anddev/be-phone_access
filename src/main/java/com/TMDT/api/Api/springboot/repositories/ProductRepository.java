package com.TMDT.api.Api.springboot.repositories;

import com.TMDT.api.Api.springboot.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByStatusNot(int id);

    Product findFirstByIdAndStatusNot(int id, int status);

    List<Product> findByNameContainingIgnoreCase(String name);


    @Query("SELECT p FROM Product p WHERE (:category IS NULL OR p.category.name = :category) AND p.status <> 0")
    Page<Product> findByCategoryAndStatusNot(@Param("category") String category, Pageable pageable);

}
