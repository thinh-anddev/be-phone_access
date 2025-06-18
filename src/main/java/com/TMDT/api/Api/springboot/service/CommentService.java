package com.TMDT.api.Api.springboot.service;

import com.TMDT.api.Api.springboot.dto.CommentDTO;
import com.TMDT.api.Api.springboot.mapper.CommentMapper;
import com.TMDT.api.Api.springboot.models.Comment;
import com.TMDT.api.Api.springboot.models.Customer;
import com.TMDT.api.Api.springboot.models.Product;
import com.TMDT.api.Api.springboot.repositories.CommentRepository;
import com.TMDT.api.Api.springboot.repositories.CustomerRepository;
import com.TMDT.api.Api.springboot.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    @Autowired
    private CommentMapper commentMapper;

    public CommentDTO addComment(CommentDTO commentDTO) {
        Product product = productRepository.findById(commentDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Customer customer = customerRepository.findById(commentDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

//        Comment comment = new Comment();
//        comment.setContent(commentDTO.getContent());
//        comment.setCreatedAt(LocalDateTime.now());
        Comment comment = commentMapper.toEntity(commentDTO);
        comment.setProduct(product);
        comment.setCustomer(customer);
        comment.setCreatedAt(LocalDateTime.now());
        return commentMapper.toDto(commentRepository.save(comment));
    }

    public List<CommentDTO> getCommentsByProductId(int productId) {
        return commentMapper.toListDto(commentRepository.findByProductId(productId));
    }

    public Page<CommentDTO> getCommentsByProductId(int productId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Comment> comments = commentRepository.findByProductId(productId, pageable);
        return comments.map(commentMapper::toDto);
    }
}
