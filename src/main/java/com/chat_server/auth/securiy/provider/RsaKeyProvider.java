package com.chat_server.auth.securiy.provider;

import com.chat_server.auth.securiy.config.RsaKeyProperties;
import com.chat_server.auth.util.RSAKeyUtils;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * packageName    : com.chat_server.auth.securiy.provider
 * fileName       : RsaKeyProvider
 * author         : parkminsu
 * date           : 25. 5. 13.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 13.        parkminsu       최초 생성
 */


@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class RsaKeyProvider {
    private final RsaKeyProperties rsaKeyProperties;

    private RSAPrivateKey privateKey;

    private RSAPublicKey publicKey;

    @PostConstruct
    public void init() throws Exception {
        log.debug("Initializing RSA private key");

        this.privateKey = RSAKeyUtils.loadPrivateKey(rsaKeyProperties.getPrivatePath());
        this.publicKey = RSAKeyUtils.loadPublicKey(rsaKeyProperties.getPublicPath());
    }



}
