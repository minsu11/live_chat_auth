package com.chat_server.auth.util;


import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
/**
 * packageName    : com.chat_server.auth.util
 * fileName       : RSAKeyUtils
 * author         : parkminsu
 * date           : 25. 5. 13.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 13.        parkminsu       최초 생성
 */
@Slf4j
public final class RSAKeyUtils {
    public static RSAPrivateKey loadPrivateKey(String path) throws Exception {
        byte[] keyBytes = readPemFile(path, "PRIVATE KEY");
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(spec);
        return (RSAPrivateKey) privateKey;
    }

    public static RSAPublicKey loadPublicKey(String path) throws Exception {
        byte[] keyBytes = readPemFile(path, "PUBLIC KEY");
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(spec);
        return (RSAPublicKey) publicKey;
    }

    private static byte[] readPemFile(String path, String keyType) throws Exception {
        String content;
        log.debug("Reading pem file {}", path);
        if (path.startsWith("classpath:")) {
            String cpPath = path.replace("classpath:", "");
            try (InputStream is = RSAKeyUtils.class.getClassLoader().getResourceAsStream(cpPath)) {
                if (is == null) throw new IllegalArgumentException("파일을 찾을 수 없음: " + cpPath);
                content = new String(is.readAllBytes());
            }
        } else {
            content = Files.readString(Paths.get(path));
        }
        content = content.replace("-----BEGIN " + keyType + "-----", "")
                .replace("-----END " + keyType + "-----", "")
                .replaceAll("\\s", "");
        return Base64.getDecoder().decode(content);
    }

}
