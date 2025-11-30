package com.proyectofinal17.datos;

import com.proyectofinal17.modelo.Contrasena;
import com.proyectofinal17.modelo.Transaccion;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseDatosLocal: almacenamiento en memoria durante la ejecución.
 * Relación de agregación con Contrasena y Transaccion.
 */
public class BaseDatosLocal {

    private final List<Contrasena> contrasenas = new ArrayList<>();
    private final List<Transaccion> transacciones = new ArrayList<>();

    public List<Contrasena> getContrasenas() { return contrasenas; }
    public List<Transaccion> getTransacciones() { return transacciones; }
}
