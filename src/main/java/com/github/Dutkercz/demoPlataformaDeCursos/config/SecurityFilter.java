package com.github.Dutkercz.demoPlataformaDeCursos.config;

import com.github.Dutkercz.demoPlataformaDeCursos.entities.User;
import com.github.Dutkercz.demoPlataformaDeCursos.services.TokenService;
import com.github.Dutkercz.demoPlataformaDeCursos.services.UserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserDetailService userDetailService;

    public SecurityFilter(TokenService tokenService, UserDetailService userDetailService) {
        this.tokenService = tokenService;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");

        //não esquecer do espaço depois do "Bearer "
        if (requestHeader != null && requestHeader.startsWith("Bearer ")){
            String cleanToken = requestHeader.replace("Bearer ", "");
            String userEmail = tokenService.getSubject(cleanToken);

            User user = (User) userDetailService.loadUserByUsername(userEmail);

            UsernamePasswordAuthenticationToken userAuth =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            if (SecurityContextHolder.getContext().getAuthentication() == null){
                SecurityContextHolder.getContext().setAuthentication(userAuth);
            }
        }
        filterChain.doFilter(request, response);
    }
}
