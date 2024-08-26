package com.personalproject1.jobportal.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        System.out.println("The username: "+username+" is logged in.");

        boolean isJobSeeker = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("Job Seeker"));
        boolean isRecruiter = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("Recruiter"));

        if(isRecruiter || isJobSeeker){
            response.sendRedirect("/dashboard/");
        }
    }
}
