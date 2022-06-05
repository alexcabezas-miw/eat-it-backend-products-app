package com.upm.miw.tfm.eatitproductsapp.config.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCredentialsDTO {
   private String username;
   private String password;
}
