package com.rupesh.blogapp.blogapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.rupesh.blogapp.blogapp.security.CustomUserDetailService;
import com.rupesh.blogapp.blogapp.security.JwtAuthenticationEntryPoint;
import com.rupesh.blogapp.blogapp.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	CustomUserDetailService customUserDetailService;
	
	@Autowired
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
		
		MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
		
		http.csrf(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests(auth -> auth.requestMatchers((mvcMatcherBuilder.pattern("/auth/login"))).permitAll().anyRequest().authenticated())
		.exceptionHandling(ex -> ex.authenticationEntryPoint(this.jwtAuthenticationEntryPoint))
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		
		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		http.authenticationProvider(daoAuthenticationProvider());
		DefaultSecurityFilterChain build = http.build();
		
		return build;
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.customUserDetailService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}
