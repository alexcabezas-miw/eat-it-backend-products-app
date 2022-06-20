package com.upm.miw.tfm.eatitproductsapp.service.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@Document(Comment.COMMENTS_COLLECTION_NAME)
public class Comment {
    public static final String COMMENTS_COLLECTION_NAME = "comments";

    @Id
    private String id;

    private String username;

    private LocalDate createdDate;

    private String content;

    private int rate;
}
