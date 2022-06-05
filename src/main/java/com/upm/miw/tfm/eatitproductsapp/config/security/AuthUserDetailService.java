package com.upm.miw.tfm.eatitproductsapp.config.security;

import com.upm.miw.tfm.eatitproductsapp.config.FeignClientsConfiguration;
import com.upm.miw.tfm.eatitproductsapp.service.auth.AuthenticationClient;
import com.upm.miw.tfm.eatitproductsapp.service.users.UsersClient;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.util.List;

@Component
public class AuthUserDetailService {

    private final AuthenticationClient authenticationClient;
    private final FeignClientsConfiguration configuration;

    public AuthUserDetailService(AuthenticationClient authenticationClient, FeignClientsConfiguration configuration) {
        this.authenticationClient = authenticationClient;
        this.configuration = configuration;
    }

    public UserDetails loadUserByUsernameAndPassword(String username, String password) {
        UserCredentialsDTO credentials = UserCredentialsDTO.builder()
                .username(username)
                .password(password).build();

        boolean authenticated = this.authenticationClient.authenticate(credentials);
        if(!authenticated) {
            throw new BadCredentialsException("Bad credentials");
        }

        UsersClient usersClient = configuration.usersClientFromAuthentication(username, password);
        List<String> roles = usersClient.getRolesByUsername(username);

        return IntegrationUser.builder()
                .username(username)
                .password(password)
                .roles(roles).build();
    }
}
