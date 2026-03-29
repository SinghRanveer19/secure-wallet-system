package com.ranveer.config;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ranveer.entity.User;
import com.ranveer.repository.UserRepository;
import com.ranveer.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserRepository userRepo;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String header = request.getHeader("Authorization");
		
		if (header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);
			String email = jwtUtil.extractEmail(token);
			
			User user = userRepo.findByEmail(email).orElseThrow();
			
			if (user != null) {
				UsernamePasswordAuthenticationToken auth = 
						new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
				
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
