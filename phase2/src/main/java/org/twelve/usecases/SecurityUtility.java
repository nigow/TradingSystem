package org.twelve.usecases;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Class dealt with encryption and decryption
 *
 * Idea of converting string to byte arrays stemmed from: https://bit.ly/2Cq8RII
 */
public class SecurityUtility {

    private final Key key;
    private final Cipher cipher;

    /**
     * initialize the key and cipher algorithms.
     * Note: if algotirhm is "AES", keyString must be a lenght of 16 characters.
     *
     * @param keyString raw String representation of key of encryption/decryption
     * @param algorithm name of algorithm
     */
    public SecurityUtility(String keyString, String algorithm) {
        Cipher cipherTemp;
        key = new SecretKeySpec(keyString.getBytes(), algorithm);

        try {
            cipherTemp = Cipher.getInstance(algorithm);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            cipherTemp = null;
        }
        cipher = cipherTemp;
    }

    /**
     * Encrypt password
     *
     * @param password a raw String representation of password
     * @return Base64 encrypted password in a String format
     */
    public String encrypt(String password){
        String encryptedString = "";
        try{
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
            encryptedString = Base64.getEncoder().encodeToString(encryptedBytes);  //new String(encryptedBytes);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return encryptedString;
    }

    /**
     * Decrypt Base64 encrypted password
     *
     * @param password encrypted text in Base64
     * @return initial raw representation of text in a String representation
     */
    public String decrypt(String password){
        String decryptedString = "";
        try{
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedByte = cipher.doFinal(Base64.getDecoder().decode(password.getBytes()));
            decryptedString = new String(decryptedByte);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return decryptedString;
    }

}
