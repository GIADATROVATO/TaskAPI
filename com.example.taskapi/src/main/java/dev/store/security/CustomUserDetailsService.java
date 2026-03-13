package dev.store.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.store.entity.User;
import dev.store.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	private final UserRepository repository;
	public CustomUserDetailsService(UserRepository u){
		this.repository=u;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u= repository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
		return new org.springframework.security.core.userdetails.User(
				u.getUsername(), u.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_"+u.getRole().name())));
	}
	/*
	 * Spring non vuole l'entity User, vuole un oggetto che implementa UserDetails 
	 * 					serve per 
	 * User 						database
	 * UserDetails					sicurezza
	 * CostumUserDetails			ponte 
	 */

}
