package com.testTaskSmartDeltaSystems.Polevoj.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserConfig {
    @Value("${applicaion.users-config.username}")
    private String username;

    @Value("${applicaion.users-config.password}")
    private String password;

    @Value("${applicaion.users-config.roles}")
    private String roles;
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username(username)
                .password(password)
                .roles(roles)
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
