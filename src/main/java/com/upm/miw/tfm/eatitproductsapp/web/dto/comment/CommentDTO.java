package com.upm.miw.tfm.eatitproductsapp.web.dto.comment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CommentDTO {
    private String username;
    private LocalDate createdDate;
    private String content;
    private int rate;
}
