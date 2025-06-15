package com.TMDT.api.Api.springboot.mapper;

import com.TMDT.api.Api.springboot.dto.CommentDTO;
import com.TMDT.api.Api.springboot.models.Comment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, CustomerMapper.class})
public interface CommentMapper {
    CommentDTO toDto(Comment comment);
    Comment toEntity(CommentDTO commentDTO);
    List<CommentDTO> toListDto(List<Comment> comments);
}