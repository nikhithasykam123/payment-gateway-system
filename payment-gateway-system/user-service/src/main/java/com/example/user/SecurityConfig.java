package com.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtFilter jwtFilter = new JwtFilter(jwtUtil);

        http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/users", "/auth/login").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    static class JwtFilter extends javax.servlet.Filter {
        private final JwtUtil jwtUtil;
        JwtFilter(JwtUtil jwtUtil){ this.jwtUtil = jwtUtil; }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            HttpServletRequest req = (HttpServletRequest) request;
            String auth = req.getHeader("Authorization");
            if(auth != null && auth.startsWith("Bearer ")) {
                try {
                    String token = auth.substring(7);
                    String sub = jwtUtil.validateAndGetSubject(token);
                    Authentication a = new UsernamePasswordAuthenticationToken(sub, null, List.of(new SimpleGrantedAuthority("USER")));
                    // set authentication (Spring Security context)
                    org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(a);
                } catch (Exception ex) {
                    ((HttpServletResponse)response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
            chain.doFilter(request, response);
        }
    }
}
