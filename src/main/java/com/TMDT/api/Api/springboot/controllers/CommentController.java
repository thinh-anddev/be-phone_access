package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.dto.CommentDTO;
import com.TMDT.api.Api.springboot.dto.CustomerDTO;
import com.TMDT.api.Api.springboot.mapper.CommentMapper;
import com.TMDT.api.Api.springboot.models.Comment;
import com.TMDT.api.Api.springboot.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    @Autowired
    private final CommentService commentService;

    @Autowired
    private CommentMapper commentMapper;
    private CustomerDTO getCurrentCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CustomerDTO) authentication.getPrincipal();
    }
    @PostMapping
    public ResponseEntity<ResponseObject> addComment(@RequestBody CommentDTO commentDTO) {
        CustomerDTO currentCustomer = getCurrentCustomer();
        commentDTO.setCustomerId(currentCustomer.getId());
        CommentDTO commentDto = commentService.addComment(commentDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseObject("ok", "Comment added successfully", commentDto));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ResponseObject> getCommentsByProduct(
            @PathVariable int productId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<CommentDTO> commentDtos = commentService.getCommentsByProductId(productId, page, size);
        return ResponseEntity
                .ok(new ResponseObject("ok", "Success", commentDtos));
    }
}
