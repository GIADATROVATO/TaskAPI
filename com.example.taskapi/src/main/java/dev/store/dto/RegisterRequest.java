package dev.store.dto;

import org.jspecify.annotations.Nullable;

import dev.store.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
/*
 * serve per la registrazione, creo un utente nel db  CLIENT -> SERVER 
 */
	@Size(min=6,max=20, message= "Password deve avere almeno 6 caratteri")
	private String password; 
	@NotBlank(message="Username obbligatorio")
	// non può essere null, vuoto, solo spazi
	private String username;
	private Role role;
	public RegisterRequest() {}
	public RegisterRequest (String password,String username,Role role) {
		 this.password=password;
		 this.username=username;
		 this.role=role;
	 }
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
}
