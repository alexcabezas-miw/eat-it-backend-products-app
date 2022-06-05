package com.upm.miw.tfm.eatitproductsapp.service.auth;

import com.upm.miw.tfm.eatitproductsapp.config.security.UserCredentialsDTO;
import feign.Headers;
import feign.RequestLine;

public interface AuthenticationClient {

    @RequestLine("POST /auth")
    @Headers("Content-Type: application/json")
    boolean authenticate(UserCredentialsDTO credentialsDTO);
}
