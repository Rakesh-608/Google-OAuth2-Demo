package com.example.UEP.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configure(http)) // Enable CORS filter
//                .csrf(AbstractHttpConfigurer::disable)
//                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .csrf(csrf-> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.sameOrigin()) // Allow embedding from same origin
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Maintain session authentication
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**", "/h2-console/**").permitAll()
                        .requestMatchers("/api/user").authenticated() // Protect profile endpoint
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect("http://localhost:4200/home"); // Redirect to Angular profile page
                        })
                )
                .logout(logout -> logout
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                        .logoutSuccessHandler((request, response, authentication) -> {
//                            new SecurityContextLogoutHandler().logout(request, response, authentication);
//                            SecurityContextHolder.clearContext();
//                            response.sendRedirect("http://localhost:4200/"); // Redirect after logout
//                        })
//                        .permitAll()
                                .logoutUrl("/api/logout") // Define logout endpoint
                                .logoutSuccessHandler((request, response, authentication) -> {
                                    new SecurityContextLogoutHandler().logout(request, response, authentication);
                                    SecurityContextHolder.clearContext();
                                    request.getSession().invalidate(); // Invalidate session
                                    response.setStatus(200);
                                })
                                .permitAll()
                );
        return http.build();
    }
}
