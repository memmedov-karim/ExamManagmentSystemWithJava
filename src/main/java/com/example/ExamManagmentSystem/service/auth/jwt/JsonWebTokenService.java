package com.example.ExamManagmentSystem.service.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.function.ServerRequest;

import java.util.Arrays;
import java.util.Date;

@Service
public class JsonWebTokenService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Getter
    @Value("${jwt.expiration}")
    private int jwtExpiration;

    private String generateToken(Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + (jwtExpiration * 1000));
        return Jwts.builder()
                .setSubject(Long.toString(userId))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Long getUserIdFromToken(HttpServletRequest request,String tokenName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            String token = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals(tokenName))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "JWT token not found in cookie"));
            Long userId = this.parseTokenAndGetUserId(token);
            return userId;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No cookies found in the request");
        }
    }

    private Long parseTokenAndGetUserId(String token) throws JwtException, NumberFormatException {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        String userIdStr = claims.getSubject();
        Long userId = Long.parseLong(userIdStr);
        return userId;
    }

    public void sendTokenWithCookie(Long id,String tokenName, HttpServletResponse response){
        String token = this.generateToken(id);
        Cookie cookie = new Cookie(tokenName, token);
        cookie.setMaxAge(this.getJwtExpiration());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        response.addCookie(cookie);
    }

    public String clearCookie(HttpServletRequest request,HttpServletResponse response,String tokenName){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(tokenName)) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    cookie.setHttpOnly(true);
                    cookie.setSecure(true);
                    response.addCookie(cookie);
                    return "Logged out successfully";
                }
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Token cookie not found");
    }
}
