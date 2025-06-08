package com.TMDT.api.Api.springboot.service;

import com.TMDT.api.Api.springboot.dto.CommentDTO;
import com.TMDT.api.Api.springboot.models.Comment;
import com.TMDT.api.Api.springboot.models.Customer;
import com.TMDT.api.Api.springboot.models.Product;
import com.TMDT.api.Api.springboot.repositories.CommentRepository;
import com.TMDT.api.Api.springboot.repositories.CustomerRepository;
import com.TMDT.api.Api.springboot.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public Comment addComment(CommentDTO commentDTO) {
        Product product = productRepository.findById(commentDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Customer customer = customerRepository.findById(commentDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setProduct(product);
        comment.setCustomer(customer);

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByProductId(int productId) {
        return commentRepository.findByProductId(productId);
    }
}
