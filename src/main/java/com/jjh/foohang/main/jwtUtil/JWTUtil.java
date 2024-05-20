package com.jjh.foohang.main.jwtUtil;

import com.jjh.foohang.member.dto.Member;
import com.jjh.foohang.member.model.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JWTUtil {
    @Value("${jwt.secret-key}")
    private String secretKeyPlain;

    private final long EXPIRATION_SECONDS = 60 * 60;	//1시간

    //application.properties에 등록된 변수
    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKeyPlain.getBytes());
    }

    // 토큰 생성
    public String generateToken(Member member) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_SECONDS * 1000);

        return Jwts.builder()
                .claim("memberId", member.getMemberId())
                .claim("email", member.getEmail())
                .claim("nickName", member.getNickName())
                .claim("region", member.getRegion())
                .claim("food", member.getFood())
                .claim("birth", member.getBirth())
                .claim("gender", member.getGender())
                .claim("statusMessage", member.getStatusMessage())
                .claim("profileName", member.getProfileName())
                //.claim("profile", member.getProfile())
                .expiration(expiration)				//만료 시간
                .signWith(getSecretKey())
                .compact();
    }

    // 토큰 유효성 검사
    public boolean isValid(String token) {
        try {
            Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            log.debug("토큰 유효성 검증 오류 : {}",e.getMessage());
            return false;
        }
    }

    //토큰으로 부터 ID 조회
    public int getMemberIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        int memberId = (Integer) claims.get("memberId");
        log.debug("claim id:{}",memberId);
        return memberId;
    }
}
