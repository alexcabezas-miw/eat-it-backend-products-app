package com.upm.miw.tfm.eatitproductsapp.config;

import com.upm.miw.tfm.eatitproductsapp.config.properties.AuthClientProperties;
import com.upm.miw.tfm.eatitproductsapp.config.properties.UsersClientProperties;
import com.upm.miw.tfm.eatitproductsapp.web.client.auth.AuthenticationClient;
import com.upm.miw.tfm.eatitproductsapp.web.client.users.UsersClient;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientsConfiguration {

    private final AuthClientProperties authClientProperties;
    private final UsersClientProperties usersClientProperties;

    public FeignClientsConfiguration(AuthClientProperties authClientProperties, UsersClientProperties usersClientProperties) {
        this.authClientProperties = authClientProperties;
        this.usersClientProperties = usersClientProperties;
    }

    @Bean
    AuthenticationClient authenticationClient() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(AuthenticationClient.class, authClientProperties.getUrl());
    }

    public UsersClient usersClientFromAuthentication(String username, String password) {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .requestInterceptor(new BasicAuthRequestInterceptor(username, password))
                .target(UsersClient.class, usersClientProperties.getUrl());
    }
}
