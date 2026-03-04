package dev.store.dto;

import org.jspecify.annotations.Nullable;

import dev.store.entity.Role;

public class RegisterRequest {
/*
 * serve per la registrazione, creo un utente nel db  CLIENT -> SERVER 
 */
	private String password; 
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
