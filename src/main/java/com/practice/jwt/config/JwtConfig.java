package com.practice.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import com.practice.jwt.repo.EmployeeRepo;
import com.practice.jwt.service.EmployeeDetailsService;

@Component
@EnableWebSecurity
@EnableMethodSecurity
public class JwtConfig {

	@Autowired
	private EmployeeRepo repo;

	@Bean
	public UserDetailsService userDetailsService() {

		return new EmployeeDetailsService(repo);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
		jwtConverter.setJwtGrantedAuthoritiesConverter(new KeyCloakRoleConverter());
		http.csrf().disable()
				.authorizeHttpRequests(requests -> requests
						.requestMatchers(new AntPathRequestMatcher("/jwt-practice/authenticate")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/jwt-practice/welcome")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/jwt-practice/save")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/jwt-practice/getEmployees")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/jwt-practice/**")).authenticated())
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().oauth2ResourceServer()
				.jwt().jwtAuthenticationConverter(jwtConverter);
		return http.build();
	}

}
