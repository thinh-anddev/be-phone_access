package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.dto.CommentDTO;
import com.TMDT.api.Api.springboot.mapper.CommentMapper;
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

    @Autowired
    private CommentMapper commentMapper;

    @PostMapping
    public ResponseEntity<ResponseObject> addComment(@RequestBody CommentDTO commentDTO) {
        Comment comment = commentService.addComment(commentDTO);
        CommentDTO commentDto = commentMapper.toDto(comment);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseObject("ok", "Comment added successfully", commentDto));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ResponseObject> getCommentsByProduct(@PathVariable int productId) {
        List<Comment> comments = commentService.getCommentsByProductId(productId);
        List<CommentDTO> commentDtos = commentMapper.toListDto(comments);
        return ResponseEntity
                .ok(new ResponseObject("ok", "Success", commentDtos));
    }
}
