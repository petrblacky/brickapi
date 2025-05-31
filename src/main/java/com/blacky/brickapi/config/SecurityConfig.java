package com.blacky.brickapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Zakázání CSRF, pokud nechcete CSRF ochranu pro veřejné API
        http.csrf().disable()
                // Povolení přístupu ke všem cestám bez autentifikace
                .authorizeRequests()
                .anyRequest().permitAll() // Povolit všechny požadavky
                .and()
                // Volitelné: Zakázání přihlašovacího formuláře (pokud není potřeba)
                .formLogin().disable()
                .httpBasic().disable(); // Pokud chcete povolit i HTTP Basic autentifikaci, můžete ji nechat povolenou.

        return http.build();
    }
}