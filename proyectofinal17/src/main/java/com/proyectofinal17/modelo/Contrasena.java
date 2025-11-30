package com.proyectofinal17.modelo;

import java.time.LocalDateTime;

/**
 * Modelo Contrasena: representa un registro de contraseña.
 * Comentarios en español en cada parte.
 */
public class Contrasena {

    private final String id;
    private final String nombre; // servicio o etiqueta
    private final String usuario;
    private final String valor; // en memoria; almacenamiento será cifrado
    private final int longitud;
    private final double precio;
    private final LocalDateTime fechaCreacion;

    public Contrasena(String id, String nombre, String usuario, String valor, int longitud, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.valor = valor;
        this.longitud = longitud;
        this.precio = precio;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters (solo los necesarios)
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getUsuario() { return usuario; }
    public String getValor() { return valor; }
    public int getLongitud() { return longitud; }
    public double getPrecio() { return precio; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }

    @Override
    public String toString() {
        return id + " | " + nombre + " | usuario: " + usuario + " | len=" + longitud + " | $" + precio;
    }
}

