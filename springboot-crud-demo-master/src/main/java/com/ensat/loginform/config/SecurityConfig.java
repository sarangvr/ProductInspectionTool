package com.ensat.loginform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public UserDetailsService userDetailsService() throws Exception {
        UserDetails admin = User.builder()
                .username("peter")
                .password(passwordEncoder().encode("pass1"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("john")
                .password(passwordEncoder().encode("pass2"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests((authorize) -> authorize
                    .antMatchers("/h2-console/**").permitAll() // Allow access to H2 Console
                    .anyRequest().authenticated())
            .formLogin().loginPage("/").permitAll()
            .and()
            .logout().permitAll(); // Enable logout

        http.csrf().disable(); // Disable CSRF for H2 Console
        http.headers().frameOptions().disable(); // Disable frame options for H2 Console

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

