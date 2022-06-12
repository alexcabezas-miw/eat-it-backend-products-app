package com.upm.miw.tfm.eatitproductsapp.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestrictionCreationDTO {

    @NotBlank
    private String name;

    @Size(min = 1)
    private List<String> ingredients;
}
