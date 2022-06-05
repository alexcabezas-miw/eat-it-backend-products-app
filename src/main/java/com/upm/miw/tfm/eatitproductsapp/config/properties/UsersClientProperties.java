package com.upm.miw.tfm.eatitproductsapp.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "eat-it.users")
public class UsersClientProperties {
    private String url;
}
