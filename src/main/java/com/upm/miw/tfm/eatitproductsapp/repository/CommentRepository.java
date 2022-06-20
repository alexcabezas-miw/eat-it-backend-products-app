package com.upm.miw.tfm.eatitproductsapp.repository;

import com.upm.miw.tfm.eatitproductsapp.service.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
