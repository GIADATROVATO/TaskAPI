package dev.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dev.store.security.CustomUserDetailsService;
import dev.store.security.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private final JwtAuthFilter jwtFilter;
	private final CustomUserDetailsService userDetailsService;
	public SecurityConfig( JwtAuthFilter jwtFilter,CustomUserDetailsService userDetailsService) {
	    this.jwtFilter = jwtFilter;
	    this.userDetailsService = userDetailsService;
	}
	@Bean
	SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception { 
		http.csrf(csr->csr.disable()).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth->auth
						.requestMatchers("/auth/**").permitAll().anyRequest().authenticated()
				)
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	 @Bean
	 AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		 return config.getAuthenticationManager();
	/*
	 * 		Questo metodo serve per gestire il login. Restituisce il componenete che: verifica username + password
	 *		Quando fai il login POST/auth/login  il Controller usa authenticationManager.authenticate(..)
	 *
	 *		SPRING prende username, carica utente dal UserDetailsService, lo confornta con passwordEncoder e se ok ---> login valido
	 */
	 }
	 @Bean
	 PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	/*
	 * 		Serve per criptare le password, usa BCrypto uno degli algoritmi piu usati.
	 * 		Quando registro un utente password= "123456" viene salvata cosi $32uqef$bp4$, quindi nel database non c'è la password vera
	 */
	 }
}
