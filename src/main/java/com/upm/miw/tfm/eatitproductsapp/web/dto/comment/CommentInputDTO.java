package com.upm.miw.tfm.eatitproductsapp.web.dto.comment;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class CommentInputDTO {

    @NotBlank
    private String content;

    @NotBlank
    private String barcode;

    @Max(5)
    private Integer rate;
}
