package com.upm.miw.tfm.eatitproductsapp.web.dto.restriction;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestrictionDTO {

    private String name;

    @Builder.Default
    private List<String> ingredients = new ArrayList<>();
}