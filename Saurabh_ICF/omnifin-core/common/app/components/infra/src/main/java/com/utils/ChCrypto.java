package com.utils;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;





import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;
 
public class ChCrypto {
 
    private static SecretKeySpec secretKey;
    private static byte[] key;
 
    public static void setKey(String myKey) 
    {
        MessageDigest sha = null;
        try {
          
        
            key = myKey.substring(0, 16).getBytes(); 
            
            secretKey = new SecretKeySpec(key, "AES");
            
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 
       
    }
 
    public static String encrypt(String strToEncrypt, String secret) 
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
           // Cipher cipher1 = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(secret.substring(0, 16).getBytes());
            SecretKeySpec sk  = new SecretKeySpec(secret.getBytes(), "AES");
            //fixKeyLength();
            cipher.init(Cipher.ENCRYPT_MODE, sk, iv);
        //    cipher1.init(Cipher.ENCRYPT_MODE, secretKey);
            
            String encoded = new BASE64Encoder().encode(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            System.out.println("encoded: "+encoded);
            /*String encoded2 = new BASE64Encoder().encode(cipher.doFinal(strToEncrypt.getBytes()));
            System.out.println(encoded2);
            String encoded1 = new BASE64Encoder().encode(cipher1.doFinal(strToEncrypt.getBytes("UTF-8")));
            System.out.println("encoded1: "+encoded1);
            */
        //    byte[] encrypted = cipher(Cipher.ENCRYPT_MODE, secret).doFinal(strToEncrypt.getBytes("UTF-8"));
                   //    encoded="AGTKSRAVnTdDpqfAO5NELrKrNyDrsgfOzdOet5XUdgi8pFZEtce9MSkIVvmf0tNKDwTeZ0r7XRhuH3gHk9a7ufvy+wfjJHJcjbRY2Oe8UsaCFG1RyUcNmtg3Ax/6f3P3MzcAGAkT8AUA4ZgkomAhjqo1VIOiqWYtuJsdO+rPt/z8fSjgsie/QBN6zG1LjonvuDYWECz4bOeWuHIUwZ/tlvuAxkVD8P9h0wXCixRcDhNDa3strcuzUujb3C/LLfwAakYtGzYd9Clae0LYJEQA34FHsYDxovu2zAUItzdsrMHoi3jqRAlj+ShpP3Ud7Np1JnEERifuROKPIlIAaUxKKA+8MuDlGYYk5/89C4I1Y7MbkuRT0Xa1BLGn77K3TPaoDVfPqzM+/CeOZPyDNP62p90mhvhiNG7YPpphLLAfLVRpwIKAGEKzz9aqDlOekL58KRsyoPt/ABI+cQTisbIzO/HRhoT2mahaXKjWwZyNmohPM4ApIqsOP5UrK2vORMF/xzR/IhL9SgsCcd7tmZGeaAa/Wio2c1xI4ZTvNrowmbJgS/kK907cGLrzUDt3wdLG4yDXs+VD9ydOf82qowItuqd4VadEkNGNtj+BSKD9AnuRTHQji/5Oe3FPAlOCqXkUCWhU/AwxVGbwK5bTB6dFXycdi9rei0/lG/4TFmgJcfGMrGt/QL0aTe4A2eU1qKFuTnWPXoSskOQ6Z4r6JWbyuhQXDHw5D6CskAhmfilRx8g=";
            
            return encoded;
        } 
        catch (Exception e) 
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
 
    public static String decrypt(String strToDecrypt, String secret) 
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(secret.substring(0, 16).getBytes());
            SecretKeySpec sk  = new SecretKeySpec(secret.getBytes(), "AES");
            //fixKeyLength();
            cipher.init(Cipher.DECRYPT_MODE, sk, iv);
           
          //  cipher.init(Cipher.DECRYPT_MODE, secretKey);
            
            return new String(cipher.doFinal(new BASE64Decoder().decodeBuffer(strToDecrypt)));
           // byte[] decodeResult = new BASE64Decoder().decodeBuffer(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
          //  System.out.println(new String(decodeResult));
            //return new String(cipher.doFinal(BASE64Decoder.decodeBuffer.(strToDecrypt)));
            //return new String(decodeResult);
        } 
        catch (Exception e) 
        {
            System.out.println("Error while decrypting: " + e.toString());
             e.printStackTrace();
        }
        return null;
    }
    public static void fixKeyLength() {
        String errorString = "Failed manually overriding key-length permissions.";
        int newMaxKeyLength;
        try {
            if ((newMaxKeyLength = Cipher.getMaxAllowedKeyLength("AES")) < 256) {
                Class c = Class.forName("javax.crypto.CryptoAllPermissionCollection");
                Constructor con = c.getDeclaredConstructor();
                con.setAccessible(true);
                Object allPermissionCollection = con.newInstance();
                Field f = c.getDeclaredField("all_allowed");
                f.setAccessible(true);
                f.setBoolean(allPermissionCollection, true);

                c = Class.forName("javax.crypto.CryptoPermissions");
                con = c.getDeclaredConstructor();
                con.setAccessible(true);
                Object allPermissions = con.newInstance();
                f = c.getDeclaredField("perms");
                f.setAccessible(true);
                ((Map) f.get(allPermissions)).put("*", allPermissionCollection);

                c = Class.forName("javax.crypto.JceSecurityManager");
                f = c.getDeclaredField("defaultPolicy");
                f.setAccessible(true);
                Field mf = Field.class.getDeclaredField("modifiers");
                mf.setAccessible(true);
                mf.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                f.set(null, allPermissions);

                newMaxKeyLength = Cipher.getMaxAllowedKeyLength("AES");
            }
        } catch (Exception e) {
            throw new RuntimeException(errorString, e);
        }
        if (newMaxKeyLength < 256)
            throw new RuntimeException(errorString); // hack failed
    }
    static private Cipher cipher(int opmode, String secretKey) throws Exception {

        if (secretKey.length() != 32)

            throw new RuntimeException("SecretKey length is not 32 chars");

        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");

        SecretKeySpec sk = new SecretKeySpec(secretKey.getBytes(), "AES");

        IvParameterSpec iv = new IvParameterSpec(secretKey.substring(0, 16).getBytes());

        c.init(opmode, sk, iv);

        return c;

    }
}