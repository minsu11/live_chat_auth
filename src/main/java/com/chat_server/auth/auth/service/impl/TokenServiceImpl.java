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
        // token
        // rsa 방식으로 할 예정, 지금 당장은 HSA 방식이 편하지만, 추 후에 msa 구조로 확장한다고 가정 했을 땐, secret 키가 유출이 되면은 보안의 위험이 있음
        Date now = new Date();
        Date accessTokenExpiration = new Date(now.getTime()+ tokenConfig.getAccessToken().toMillis());

        Date refreshTokenExpiration = new Date(now.getTime() + tokenConfig.getRefreshToken().toMillis());

        String accessToken = buildToken(userId, accessTokenJti, accessTokenExpiration, rsaKeyProvider.getPrivateKey());
        String refreshToken = buildToken(userId, refreshTokenJti, refreshTokenExpiration, rsaKeyProvider.getPrivateKey());


        return TokenPair.of(accessToken, refreshToken,accessTokenExpiration,refreshTokenExpiration);
    }
    private String buildToken(String subject, String jti, Date expiration, PrivateKey privateKey) {
        return Jwts.builder()
                .setSubject(subject)
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
            return Jwts.parserBuilder()
                    .setSigningKey(rsaKeyProvider.getPublicKey()) // RSA Public Key
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // 만료가 된 access token을 가지고 와서 subject에 저장된 user id (uuid) 꺼낼 예정
            return e.getClaims();
        }
    }
}
