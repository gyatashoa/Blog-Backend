package com.example.blogbackend.utils;


import com.example.blogbackend.models.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtUtil {


    private String SECRET_KEY = "secret";

    public String extractEmail(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Object extractUserId(String token){ return extractClaim(token,(claims) ->claims.get("user_id"));}

    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String generateToken(AppUser user){
        //claims can contain the payload we want
        Map<String,Object> claims = new HashMap<>();
        claims.put("user_id",user.getId());
        return createToken(claims,user.getUsername());
    }

    private String createToken(Map<String,Object> claims,String subject){
        return  Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractEmail(token);
        return username.equals(userDetails.getUsername());
    }


}
