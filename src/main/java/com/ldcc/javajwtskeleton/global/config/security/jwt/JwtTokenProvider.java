package com.ldcc.javajwtskeleton.global.config.security.jwt;

import com.ldcc.javajwtskeleton.domain.member.dto.Token;
import com.ldcc.javajwtskeleton.domain.member.entity.Member;
import com.ldcc.javajwtskeleton.domain.member.repository.MemberRepository;
import com.ldcc.javajwtskeleton.domain.member.dto.auth.CustomUserDetails;
import com.ldcc.javajwtskeleton.global.exceptions.CustomAutenticationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;
    private final long expireTime;
    private final long refreshExpireTime;
    private final MemberRepository memberRepository;

    public JwtTokenProvider(@Value("${jwt.secret_key}") String secretKey
            , @Value("${jwt.expire_time}") long expireTime
            , @Value("${jwt.refresh_expire_time}") long refreshExpireTime,
                            MemberRepository memberRepository) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.expireTime = expireTime;
        this.refreshExpireTime = refreshExpireTime;
        this.memberRepository = memberRepository;
    }

    public Token createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // access token 생성
        String accessToken = this.createAccessToken(authentication, authorities);

        // refresh token 생성
        String refreshToken = this.createRefreshToken();

        return Token.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String createAccessToken(Authentication authentication, String authorities) {
        Date accessTokenExpire = new Date(new Date().getTime() + this.expireTime);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpire)
                .signWith(this.key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken() {
        Date refreshTokenExpire = new Date(new Date().getTime() + this.refreshExpireTime);

        return Jwts.builder()
                .setExpiration(refreshTokenExpire)
                .signWith(this.key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = validateToken(accessToken);
        if(claims.get("auth") == null) {
            throw new CustomAutenticationException("권한 정보가 없는 토큰입니다.");
        }

        Member member = memberRepository.findByEmail(claims.getSubject()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자 입니다."));
        CustomUserDetails userDetails = new CustomUserDetails(member);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getNameByToken(String token) {
        Claims claims = this.validateToken(token);
        return claims.getSubject();
    }

    // 토큰 정보 검증
    public Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(this.key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException | MalformedJwtException |io.jsonwebtoken.security.SignatureException e) {
            throw new CustomAutenticationException("토큰 값이 잘못되었습니다.", e);
        } catch (ExpiredJwtException e) {
            throw new CustomAutenticationException("토큰 값이 만료되었습니다.", e);
        } catch (UnsupportedJwtException e) {
            throw new CustomAutenticationException("지원하지 않는 Jwt 입니다.", e);
        } catch (IllegalArgumentException e) {
            throw new CustomAutenticationException("내용이 존재하지 않습니다.", e);
        }
    }

    public String resolveToken(String token) {
        if(!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }
        return token.substring(7);
    }
}
