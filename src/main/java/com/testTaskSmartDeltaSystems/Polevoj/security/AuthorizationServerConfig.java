package com.testTaskSmartDeltaSystems.Polevoj.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AuthorizationServerConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity

                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("students/**").authenticated()
                                .anyRequest().denyAll()
                )
                .formLogin(Customizer.withDefaults()
                );
        return httpSecurity.build();
    }
}
