/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.proyectofinal17;

import com.proyectofinal17.datos.BaseDatosLocal;
import com.proyectofinal17.datos.GestorArchivos;
import com.proyectofinal17.pdf.PDFPrecios;
import com.proyectofinal17.pdf.PDFReporte;
import com.proyectofinal17.pdf.PDFTransacciones;
import com.proyectofinal17.seguridad.EncriptadorAES;
import com.proyectofinal17.seguridad.GeneradorContrasenas;
import com.proyectofinal17.seguridad.ValidadorContrasenas;

import java.util.List;

/**
 * Clase principal: inicializa componentes y lanza el menú consola.
 */
public class Principal {

    public static void main(String[] args) {

        // Componentes centrales
        BaseDatosLocal base = new BaseDatosLocal();
        GestorArchivos gestorArchivos = new GestorArchivos("storage_contrasenas.dat");
        EncriptadorAES encriptador = new EncriptadorAES();
        GeneradorContrasenas generador = new GeneradorContrasenas();
        ValidadorContrasenas validador = new ValidadorContrasenas();

        // Cargar datos guardados (si existe)
        try {
            List<com.proyectofinal17.modelo.Contrasena> cargadas = gestorArchivos.cargar(encriptador);
            base.getContrasenas().addAll(cargadas);
            System.out.println("Se cargaron " + cargadas.size() + " contraseñas desde almacenamiento.");
        } catch (Exception e) {
            System.out.println("No se pudieron cargar contraseñas: " + e.getMessage());
        }

        // Instancias PDF y GUI
        PDFReporte pdfReporte = new PDFReporte();
        PDFPrecios pdfPrecios = new PDFPrecios();
        PDFTransacciones pdfTrans = new PDFTransacciones();

        // Iniciar menú consola (usa los servicios creados)
        MenuConsola menu = new MenuConsola(base, gestorArchivos, encriptador, generador, validador, pdfReporte, pdfPrecios, pdfTrans);
        menu.mostrarMenu();

        // Nota: la GUI (VentanaPrincipal) también puede abrirse desde el menú
        System.out.println("Programa finalizado.");
    }
}
