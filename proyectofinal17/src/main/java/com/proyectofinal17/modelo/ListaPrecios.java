package com.proyectofinal17.modelo;

/**
 * Clase utilitaria para calcular precio según longitud.
 */
public class ListaPrecios {

    public static double calcularPrecio(int longitud) {
        if (longitud <= 6) return 1000.0;
        if (longitud <= 10) return 2000.0;
        if (longitud <= 15) return 3500.0;
        return 5000.0;
    }
}
