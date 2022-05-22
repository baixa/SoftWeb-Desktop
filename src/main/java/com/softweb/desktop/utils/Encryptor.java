package com.softweb.desktop.utils;
import org.apache.commons.codec.digest.DigestUtils;

public class Encryptor {
    public static String hashSHA(String value) {
        return DigestUtils.sha1Hex(value);
    }
}
