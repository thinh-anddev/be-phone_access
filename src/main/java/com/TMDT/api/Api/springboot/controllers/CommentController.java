package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.dto.CommentDTO;
import com.TMDT.api.Api.springboot.models.Comment;
import com.TMDT.api.Api.springboot.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    @Autowired
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody CommentDTO commentDTO) {
        Comment comment = commentService.addComment(commentDTO);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Comment>> getCommentsByProduct(@PathVariable int productId) {
        List<Comment> comments = commentService.getCommentsByProductId(productId);
        return ResponseEntity.ok(comments);
    }
}
