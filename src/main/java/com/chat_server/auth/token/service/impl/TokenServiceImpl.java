package com.chat_server.auth.token.service.impl;

import com.chat_server.auth.securiy.provider.RsaKeyProvider;
import com.chat_server.auth.token.config.TokenConfig;
import com.chat_server.auth.token.dto.response.TokenPair;
import com.chat_server.auth.token.service.TokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

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
        Date accessTokenExpiration = new Date(now.getTime()+ Long.parseLong(tokenConfig.getAccessToken().getExpiration())*1000);

        Date refreshTokenExpriation = new Date(now.getTime() + Long.parseLong(tokenConfig.getRefreshToken().getExpiration()) * 1000);


        String accessToken = Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .claim("jti",accessTokenJti)
                .setExpiration(accessTokenExpiration)
                .signWith(SignatureAlgorithm.RS256, rsaKeyProvider.getPrivateKey())
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .claim("jti",refreshTokenJti)
                .setExpiration(refreshTokenExpriation)
                .signWith(SignatureAlgorithm.RS256, rsaKeyProvider.getPrivateKey())
                .compact();
        TokenPair tokenPair = new TokenPair(accessToken, refreshToken);
        return tokenPair;
    }

    //1회성 방법?
    @Override
    public String regenerateAccessToken(String refreshToken) {
        return "";
    }

    @Override
    public String regenerateRefreshToken(String oldRefreshToken) {
        return "";
    }

    @Override
    public void invalidateRefreshToken(String refreshToken) {

    }

    @Override
    public boolean isValidToken(String token) {
        return false;
    }
}
