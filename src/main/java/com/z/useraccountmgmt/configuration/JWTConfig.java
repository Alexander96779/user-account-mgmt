package com.z.useraccountmgmt.configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.z.useraccountmgmt.model.User;
import com.z.useraccountmgmt.model.response.AuthResponse;
import com.z.useraccountmgmt.service.AuthService;
import com.z.useraccountmgmt.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTConfig {
    private String SECRET_KEY = "secret";

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;


    public String extractUsername(String token) throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String userToString = extractClaim(token, Claims::getSubject);
        String email;
        User user = mapper.readValue(userToString, User.class);
        email = user.getEmail();
        return email;
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) throws JsonProcessingException {
        Map<String, Object> claims = new HashMap<>();
        String jsonStr;
        ObjectMapper Obj = new ObjectMapper();
        AuthResponse authResponse = authService.mapToResponse(userDetails.getUsername());
        jsonStr = Obj.writeValueAsString(authResponse);
        return createToken(claims, jsonStr);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails)
            throws JsonMappingException, JsonProcessingException {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
