package org.twelve.usecases;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

//reference: https://stackoverflow.com/questions/23561104/how-to-encrypt-and-decrypt-string-with-my-passphrase-in-java-pc-not-mobile-plat
/**
 * Class dealt with encryption and decryption
 */
public class SecurityUtility {

    private final Key aes;
    private final Cipher cipher;


    // key must be a 16 bytes key if using "AES"
    public SecurityUtility(String key, String algorithm) {
        Cipher cipherTemp;
        aes = new SecretKeySpec(key.getBytes(), algorithm);

        try {
            cipherTemp = Cipher.getInstance(algorithm);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            cipherTemp = null;
        }
        cipher = cipherTemp;
    }

    public String encrypt(String password){
        String encryptedString = "";
        try{
            cipher.init(Cipher.ENCRYPT_MODE, aes);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
            encryptedString = Base64.getEncoder().encodeToString(encryptedBytes);  //new String(encryptedBytes);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return encryptedString;
    }

    public String decrypt(String password){
        String decryptedString = "";
        try{
            cipher.init(Cipher.DECRYPT_MODE, aes);
            byte[] decryptedByte = cipher.doFinal(Base64.getDecoder().decode(password.getBytes()));
            decryptedString = new String(decryptedByte);
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return decryptedString;
    }

}
