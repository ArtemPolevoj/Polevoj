package com.testTaskSmartDeltaSystems.Polevoj.security;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;
@Configuration
public class AuthorizationServerConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity

                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("students/**").authenticated() //permitAll()
//                                .requestMatchers("persons/**", "admin/**", "v3/api-docs/**", "swagger-ui/**").hasAnyAuthority("ADMIN")
//                                .requestMatchers("/students/**").hasAnyAuthority("USER", "ADMIN")
 //                               .requestMatchers("auth/**", "any/**", "/", "index/**").authenticated()
                                .anyRequest().denyAll()
                )
                .formLogin(Customizer.withDefaults()
                );
        return httpSecurity.build();
    }
}
