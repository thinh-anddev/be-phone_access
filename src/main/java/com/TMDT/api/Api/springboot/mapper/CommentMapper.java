package com.TMDT.api.Api.springboot.mapper;

import com.TMDT.api.Api.springboot.dto.CommentDTO;
import com.TMDT.api.Api.springboot.models.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, CustomerMapper.class})
public interface CommentMapper {
    @Mapping(target = "username", source = "customer.username")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "customerId", source = "customer.id")
    CommentDTO toDto(Comment comment);

    Comment toEntity(CommentDTO commentDTO);

    List<CommentDTO> toListDto(List<Comment> comments);
}