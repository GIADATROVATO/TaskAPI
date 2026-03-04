package dev.store.service;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.store.dto.AuthRequest;
import dev.store.dto.AuthResponse;
import dev.store.dto.RegisterRequest;
import dev.store.entity.Role;
import dev.store.entity.User;
import dev.store.repository.UserRepository;
import dev.store.security.JwtService;

public class AuthService {
	
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService; 
	private final AuthenticationManager authenticationManager;
	
	public AuthService(UserRepository repository,PasswordEncoder passwordEncoder,
				JwtService jwtService,AuthenticationManager authenticationManager) {
		this.repository=repository;
		this.passwordEncoder=passwordEncoder;
		this.jwtService=jwtService;
		this.authenticationManager=authenticationManager;
		
	}
	public AuthResponse register(RegisterRequest request) {
		if(repository.existsByUsername(request.getUsername())) {
			throw new RuntimeException("username already exists");
		}
		User u= new User();
		u.setUsername(request.getUsername());
		u.setPassword(passwordEncoder.encode(request.getPassword()));
		u.setRole(Role.USER);
		repository.save(u);
		
		String token = jwtService.generateToken(u.getUsername(), u.getRole().name());
		
		return new AuthResponse(
				token,
				u.getUsername(),
				u.getRole().name());
	}
	public AuthResponse login(AuthRequest request) {
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
		
		User u= repository.findByUsername(request.getUsername()).orElseThrow(()-> new RuntimeException("user not found"));
		String token= jwtService.generateToken(u.getUsername(),u.getRole().name());
		return new AuthResponse( 
				token,
				u.getUsername(),
				u.getRole().name());
	}
	
}
