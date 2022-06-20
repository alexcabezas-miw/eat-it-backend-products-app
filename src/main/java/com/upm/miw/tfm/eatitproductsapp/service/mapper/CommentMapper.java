package com.upm.miw.tfm.eatitproductsapp.service.mapper;

import com.upm.miw.tfm.eatitproductsapp.service.model.Comment;
import com.upm.miw.tfm.eatitproductsapp.web.dto.comment.CommentInputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment fromCommentInput(CommentInputDTO commentInputDTO);
}
