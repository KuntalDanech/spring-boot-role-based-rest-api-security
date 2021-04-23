package com.danech.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Basic JWT token utilities goes here.
 * @author dev77
 *
 */
@Component
public class JwtUtil {
	
	@Value("${app.secret}")
	private String secret;
	
	@Value("${app.issuer}")
	private String issuer;

	//6. Validate token
	public boolean validateToken(String token,String dbUserName) {
		String userName = getUserName(token);
		return (userName.equals(dbUserName) && !isTokenExp(token));
	}
	
	//5. Validate expiration date
	public boolean isTokenExp(String token) {
		Date expDate = getExpDate(token);
		return expDate.before(new Date(System.currentTimeMillis()));
	}
	// 4. Rad subject/username
	public String getUserName(String token) {
		return getClaims(token).getSubject();
	}
	// 3. Read expiration Date
	public Date getExpDate(String token) {
		return getClaims(token).getExpiration();		
	}
	//2. Read claims
	public Claims getClaims(String token) {
		
		return Jwts.parser()
				.setSigningKey(secret.getBytes())
				.parseClaimsJws(token)
				.getBody()
				;
	}	
	//1.  generate token
	public String generateToken(String subject) {
		
		return Jwts.builder()
				.setSubject(subject)
				.setIssuer(issuer)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15)))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
}
