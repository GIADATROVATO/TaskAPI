package dev.store.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.store.dto.AuthRequest;
import dev.store.dto.AuthResponse;
import dev.store.dto.RegisterRequest;
import dev.store.service.AuthService;
import jakarta.validation.Valid;
import payload.ApiResponse;
@RestController
@RequestMapping("/auth")
public class AuthController {
	private AuthService service;
	public AuthController(AuthService service) {
		this.service=service;
	}
/*
 * uso @Valid perche ho oggetti complessi da validare, devo validare lunghezza, password ecc, 
 * in TaskController ho campi primitivi	
 */
	@PostMapping("/register")
	public ResponseEntity<ApiResponse<AuthResponse>> registerUser(@Valid @RequestBody RegisterRequest request) {	//201 register
		AuthResponse response= service.register(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response, "User registered successfully"));
	}
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<AuthResponse>> loginUser(@Valid @RequestBody AuthRequest request) {		//200 login
		AuthResponse response= service.login(request);
		return ResponseEntity.ok(ApiResponse.success(response, "Login successful"));
	}
	/*
	 * con ResponseEntity posso controllare lo status code ( 200,201,400,401...) 
	 * 201 CREATED 400 BAD REQUEST 401 UNAUTHORIZED 204 NO CONTENT 
	 * altrimenti:  Status: 200 OK 
	 * 				Body: AuthResponde JSON
	 */
	
	/*
	 *  aggiungendo ApiResponse
	 *  {
	 *  "response": 
	 *  "message": 
	 *  "data": {
	 *  	"token":
	 *  	"username": 
	 *  	}
	 *  }
	 *  
	 *  senza: 
	 *  {
	 *  	"token":
	 *  	"username": 
	 *  }
	 */
}
