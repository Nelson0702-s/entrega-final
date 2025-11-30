package com.proyectofinal17.seguridad;

/**
 * Validador básico de contraseñas.
 */
public class ValidadorContrasenas {

    public boolean esSegura(String pass) {
        if (pass == null || pass.length() < 6) return false;
        boolean may = pass.matches(".*[A-Z].*");
        boolean min = pass.matches(".*[a-z].*");
        boolean dig = pass.matches(".*[0-9].*");
        return may && min && dig;
    }
}
