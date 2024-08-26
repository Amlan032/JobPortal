package com.personalproject1.jobportal.config;

import com.personalproject1.jobportal.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }
    private final String[] publicUrl = {
            "/",
            "/global-search/**",
            "/register",
            "/register/**",
            "/webjars/**",
            "/resources/**",
            "/assets/**",
            "/css/**",
            "/summernote/**",
            "/js/**",
            "/*.css",
            "/*.js",
            "/*.js.map",
            "/fonts**", "/favicon.ico", "/resources/**", "/error"};


    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authenticationProvider(customAuthenticationProvider());

        //basically allowing free access to only the public urls
        httpSecurity.authorizeHttpRequests(auth -> {
            auth.requestMatchers(publicUrl).permitAll();
            auth.anyRequest().authenticated();
        });

        httpSecurity.formLogin(form -> form.loginPage("/login").permitAll()
                        .successHandler(customAuthenticationSuccessHandler))
                    .logout(logout -> {
                        logout.logoutUrl("/logout");
                        logout.logoutSuccessUrl("/");
                    })
                    .cors(Customizer.withDefaults())
                    .csrf(csrf -> csrf.disable());

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        //provides custom authenticator
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        //tells spring security how to retrieve users from db
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //tells spring security how to authenticate the passwords (plain text or encrypted)
        return new BCryptPasswordEncoder();
    }
}
