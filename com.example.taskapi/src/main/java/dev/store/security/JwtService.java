package dev.store.security;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import dev.store.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {
	private final Key key= Keys.hmacShaKeyFor("my-super-secret-jwt-key-that-is-long-enough-256bits".getBytes());
	
	
	public String generateToken(String username, String role) {
		return Jwts.builder().setSubject(username).claim("role",role).setIssuedAt(new Date()).setExpiration(new Date(
				System.currentTimeMillis()+360000)).signWith(key,SignatureAlgorithm.HS256).compact();
	}
	
	public Claims extract(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token).getBody();
	}
	/*
	 * UserDetails è un' interfaccia di Spring security 
	 * faccio anche questo controllo perche non è sicuro che il token non sia scaduto e che appartenga all'utente corretto
	 */
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		try {
			Claims claim= extract(token);
			String username= claim.getSubject();
			Date expiration= claim.getExpiration();
			return username.equals(userDetails.getUsername()) && expiration.after(new Date());	//la data di scadenza è DOPO adesso?
		}catch (JwtException | IllegalArgumentException e) {
            return false;
        }
	}
	/*
	public String extractUsername(String token) { return extract(token).getSubject(); }
	public Date extractExpiration(String token) { return extract(token).getExpiration();}
	private boolean isTokenExpired(String token) { return extractExpiration(token).before(new Date());}
	public boolean tokenValid(String token, UserDetails userDetails) {
		try {
			final String username= extractUsername(token);
			return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
		}catch(JwtException | IllegalArgumentException e) { return false;}
	}
	*/
	
}
