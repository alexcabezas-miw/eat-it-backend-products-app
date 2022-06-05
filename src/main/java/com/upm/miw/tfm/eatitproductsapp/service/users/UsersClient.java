package com.upm.miw.tfm.eatitproductsapp.service.users;

import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface UsersClient {

    @RequestLine("GET /users/roles/{username}")
    List<String> getRolesByUsername(@Param("username") String username);
}
