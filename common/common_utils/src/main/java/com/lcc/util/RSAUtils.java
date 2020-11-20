package com.lcc.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Administrator
 * @Description RSA工具类,生成公钥和私钥,加密与解密
 * @Date 2020/9/21
 */
public class RSAUtils {

    public static final String PUBLIC_KEY = "PUBLIC_KEY"; // 公钥
    public static final String PRIVATE_KEY = "PRIVATE_KEY"; // 私钥

//    public static void main(String[] args) {
//        //生成公钥和私钥
//        Map<String, String> map = genKeyPair();
//
//        //加密字符串
//        String message = "98520@*&..985";
//
//        System.out.println("随机生成的公钥为:" + map.get(RSAUtils.PUBLIC_KEY));
//        System.out.println("随机生成的私钥为:" + map.get(RSAUtils.PRIVATE_KEY));
//
//        String messageEn = encrypt(message, map.get(RSAUtils.PUBLIC_KEY));
//
//        System.out.println("加密后的字符串为:" + messageEn);
//
//        String messageDe = decrypt(messageEn, map.get(RSAUtils.PRIVATE_KEY));
//        System.out.println("还原后的字符串为:" + messageDe);
//    }

    //随机生成密钥对
    public static Map<String, String> genKeyPair() {
        Map<String, String> keyMap = new HashMap<>(); // 用于封装随机产生的公钥与私钥

        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 初始化密钥对生成器
        assert keyPairGen != null;
        keyPairGen.initialize(512, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair(); // 生成一个密钥对，保存在keyPair中
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded())); // 得到公钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));  // 得到私钥字符串
        // 将公钥和私钥保存到Map
        keyMap.put(RSAUtils.PUBLIC_KEY, publicKeyString);
        keyMap.put(RSAUtils.PRIVATE_KEY, privateKeyString);
        return keyMap;
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     */
    public static String encrypt(String str, String publicKey) {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = null;
        String outStr = null;
        try {
            pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
        } catch (InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //RSA加密
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 铭文
     */
    public static String decrypt(String str, String privateKey) {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes(StandardCharsets.UTF_8));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        RSAPrivateKey priKey = null;
        //RSA解密
        Cipher cipher = null;
        String outStr = null;

        try {
            priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            outStr = new String(cipher.doFinal(inputByte));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return outStr;
    }

}
