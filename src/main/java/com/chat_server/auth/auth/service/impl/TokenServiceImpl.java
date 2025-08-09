package com.chat_server.auth.auth.service.impl;

import com.chat_server.auth.auth.exception.UnauthorizedException;
import com.chat_server.auth.securiy.provider.RsaKeyProvider;
import com.chat_server.auth.auth.config.TokenConfig;
import com.chat_server.auth.auth.dto.response.TokenPair;
import com.chat_server.auth.auth.service.TokenService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.Date;

/**
 * packageName    : com.chat_server.auth.token.service.impl
 * fileName       : TokenServiceImple
 * author         : parkminsu
 * date           : 25. 5. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 11.        parkminsu       최초 생성
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenConfig tokenConfig;
    private final RsaKeyProvider rsaKeyProvider;
    // todo#3 token 발급 데이터를 뭘 넣어야할 지 고민
    @Override
    public TokenPair generateTokenPair(String userId, String accessTokenJti, String refreshTokenJti) {

        Date now = new Date();
        Date accessTokenExpiration = new Date(now.getTime()+ tokenConfig.getAccessToken().toMillis());

        Date refreshTokenExpiration = new Date(now.getTime() + tokenConfig.getRefreshToken().toMillis());

        String accessTokenIss = tokenConfig.getAccessToken().getIss();
        String refreshTokenIss = tokenConfig.getRefreshToken().getIss();

        PrivateKey rsaPrivateKey = rsaKeyProvider.getPrivateKey();

        String accessToken = buildToken(userId, accessTokenIss,accessTokenJti, accessTokenExpiration, rsaPrivateKey);

        String refreshToken = buildToken(userId,refreshTokenIss, refreshTokenJti, refreshTokenExpiration, rsaPrivateKey);

        return TokenPair.of(accessToken, refreshToken,accessTokenExpiration,refreshTokenExpiration);
    }
    private String buildToken(String subject,String issuer, String jti, Date expiration, PrivateKey privateKey) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .claim("jti", jti)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    @Override
    public void invalidateRefreshToken(String refreshToken) {

    }

    @Override
    public void validToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(rsaKeyProvider.getPublicKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException e) {
            throw new UnauthorizedException("Invalid Refresh Token");
        }
    }

    @Override
    public Claims extractClaimsAllowExpired(String token) {
        try {
            return extractClaims(token);
        } catch (ExpiredJwtException e) {
            // 만료가 된 access token을 가지고 와서 subject에 저장된 user id (uuid) 꺼낼 예정
            return e.getClaims();
        }
    }
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(rsaKeyProvider.getPublicKey()) // RSA Public Key
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
