package com.proyectofinal17.modelo;

import java.time.LocalDateTime;

/**
 * Modelo Transaccion: registra operaciones con monto y fecha.
 */
public class Transaccion {

    private final String id;
    private final String tipo;
    private final double monto;
    private final LocalDateTime fecha;

    public Transaccion(String tipo, double monto) {
        this.id = java.util.UUID.randomUUID().toString();
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = LocalDateTime.now();
    }

    public String getId() { return id; }
    public String getTipo() { return tipo; }
    public double getMonto() { return monto; }
    public LocalDateTime getFecha() { return fecha; }

    @Override
    public String toString() {
        return fecha + " | " + tipo + " | $" + monto;
    }
}
