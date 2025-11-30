package com.proyectofinal17.seguridad;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * EncriptadorAES: cifrado AES simple para demostración.
 * EN PRODUCCIÓN: usar AES-GCM y derivación de claves (PBKDF2).
 */
public class EncriptadorAES {

    // CLAVE DE EJEMPLO (16 bytes). Cambiar en producción.
    private static final String CLAVE = "0123456789ABCDEF";
    private static final String ALGORITMO = "AES/ECB/PKCS5Padding";

    public String cifrar(String texto) throws Exception {
        SecretKeySpec key = new SecretKeySpec(CLAVE.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] enc = cipher.doFinal(texto.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(enc);
    }

    public String descifrar(String b64) throws Exception {
        SecretKeySpec key = new SecretKeySpec(CLAVE.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance(ALGORITMO);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] dec = Base64.getDecoder().decode(b64);
        return new String(cipher.doFinal(dec), "UTF-8");
    }
}
