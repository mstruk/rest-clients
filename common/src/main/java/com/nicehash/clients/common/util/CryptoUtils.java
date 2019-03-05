package com.nicehash.clients.common.util;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Marko
 * @author Ales
 */
public class CryptoUtils {
    private final static Logger log = LoggerFactory.getLogger(CryptoUtils.class);

    private static final String HMAC_SHA256 = "HmacSHA256";

    public static String hashBySegments(String key, String apiKey, String time, String nonce, String method, String encodedPath, String query, String bodyStr) {

        List<byte []> segments = new ArrayList<>();
        segments.add(apiKey.getBytes(ISO_8859_1));
        segments.add(time.getBytes(ISO_8859_1));
        segments.add(nonce.getBytes(ISO_8859_1));
        segments.add(null);  // unused field
        segments.add(null);  // unused field
        segments.add(null);  // unused field
        segments.add(method.getBytes(ISO_8859_1));
        segments.add(encodedPath == null ? null : encodedPath.getBytes(ISO_8859_1));
        segments.add(query == null ? null : query.getBytes(ISO_8859_1));

        if (bodyStr != null && bodyStr.length() > 0) {
            segments.add(bodyStr.getBytes(UTF_8));
        }

        return hmacSha256BySegments(key, segments);
    }

    private static String hmacSha256BySegments(String key, List<byte []> segments) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(UTF_8), HMAC_SHA256);
            mac.init(secret_key);
            boolean first = true;
            for (byte [] segment: segments) {
                if (!first) {
                    mac.update((byte) 0);
                    //baos.write((byte) 0);
                } else {
                    first = false;
                }
                if (segment != null) {
                    mac.update(segment);
                    //baos.write(segment);
                }
            }

            if (log.isDebugEnabled()) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                first = true;
                for (byte [] segment: segments) {
                    if (!first) {
                        baos.write((byte) 0);
                    } else {
                        first = false;
                    }
                    if (segment != null) {
                        baos.write(segment);
                    }
                }
                log.debug("Hash input: [{}]", baos.toString(ISO_8859_1.name()));
            }

            return Hex.encodeHexString(mac.doFinal());
        } catch (Exception e) {
            throw new RuntimeException("Cannot create HmacSHA256", e);
        }
    }

    public static String generateWsKey() {
        byte[] nonce = new byte[16];
        new SecureRandom().nextBytes(nonce);
        return Base64.getEncoder().encodeToString(nonce);
    }
}