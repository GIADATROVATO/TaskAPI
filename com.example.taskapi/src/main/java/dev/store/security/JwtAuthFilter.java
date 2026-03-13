package dev.store.security;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
/*
 *  crea automaticamente questo oggetto e gestiscilo come Bean, serve per iniettare il filtro in securityConfig 
 */
public class JwtAuthFilter extends OncePerRequestFilter {	// questo filtro si fa una volta per ogni richiesta http 
	private final JwtService jwt;
	/*
	 * così carico l'utente dal db 
	 */
	private final UserDetailsService userDetailsService;
	public JwtAuthFilter(JwtService jwtService,UserDetailsService userDetailsService ) {	//Spring inietta JWT e il servizio utenti
		this.jwt=jwtService;
		this.userDetailsService=userDetailsService;
	}
	
	@Override
	/*
	 * ogni volta che viene eseguita una rihciesta: GET o POST ecc, Spring passa da qui
	 */
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	throws ServletException, IOException {
		
		String auth= request.getHeader("Authorization");
			
		if(auth==null && !auth.startsWith("Bearer ")){	//non c'è + header, non c'è + token > no autenticazione > passo al prossimo filtro
			filterChain.doFilter(request, response);	// continua la catena di filtri senza autenticare
			return;
		}
		String token= auth.substring(7);
		try {
			String username= jwt.extract(token).getSubject();										//estrae info per autorizzare l'utente
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails userDetails= userDetailsService.loadUserByUsername(username);			//query al db, recupero un utente reale
			
			//  controllo se la firma è corretta, token non scaduto, username coincide 
			 
				if(jwt.isTokenValid(token, userDetails)) {
				/* se il token è valido, creo un oggetto authentication  
				 * >>> l'utente è autenticato, dentro contiene userdetails e ruoli(authorities)
				 * 
				 */
					UsernamePasswordAuthenticationToken u = 
					new UsernamePasswordAuthenticationToken(
													userDetails,
													null, 
													userDetails.getAuthorities()
					);
					SecurityContextHolder.getContext().setAuthentication(u);
				}
			}	
		}catch(Exception e) {}
		filterChain.doFilter(request, response);	
	}
	
	
	
}
