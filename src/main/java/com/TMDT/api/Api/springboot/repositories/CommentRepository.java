package com.TMDT.api.Api.springboot.repositories;

import com.TMDT.api.Api.springboot.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByProductId(int productId);
}