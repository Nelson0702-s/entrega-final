package com.proyectofinal17.seguridad;

import java.security.SecureRandom;

/**
 * GeneradorContrasenas: genera contraseñas seguras aleatorias.
 */
public class GeneradorContrasenas {

    private static final String MAY = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String MIN = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIG = "0123456789";
    private static final String SYM = "!@#$%&*()-_=+";
    private static final String ALL = MAY + MIN + DIG + SYM;
    private final SecureRandom rnd = new SecureRandom();

    public String generar(int longitud) {
        if (longitud < 4) longitud = 4;
        StringBuilder sb = new StringBuilder(longitud);
        // Garantizar un caracter de cada tipo si es posible
        sb.append(MAY.charAt(rnd.nextInt(MAY.length())));
        sb.append(MIN.charAt(rnd.nextInt(MIN.length())));
        sb.append(DIG.charAt(rnd.nextInt(DIG.length())));
        sb.append(SYM.charAt(rnd.nextInt(SYM.length())));
        for (int i = 4; i < longitud; i++) {
            sb.append(ALL.charAt(rnd.nextInt(ALL.length())));
        }
        // Mezclar
        char[] arr = sb.toString().toCharArray();
        for (int i = arr.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            char tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
        }
        return new String(arr);
    }
}
