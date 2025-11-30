package com.proyectofinal17;

import com.proyectofinal17.datos.BaseDatosLocal;
import com.proyectofinal17.datos.GestorArchivos;
import com.proyectofinal17.gui.VentanaPrincipal;
import com.proyectofinal17.modelo.Contrasena;
import com.proyectofinal17.modelo.ListaPrecios;
import com.proyectofinal17.pdf.PDFPrecios;
import com.proyectofinal17.pdf.PDFReporte;
import com.proyectofinal17.pdf.PDFTransacciones;
import com.proyectofinal17.seguridad.EncriptadorAES;
import com.proyectofinal17.seguridad.GeneradorContrasenas;
import com.proyectofinal17.seguridad.ValidadorContrasenas;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * Menú por consola con todas las opciones solicitadas.
 */
public class MenuConsola {

    private final BaseDatosLocal base;
    private final GestorArchivos gestorArchivos;
    private final EncriptadorAES encriptador;
    private final GeneradorContrasenas generador;
    private final ValidadorContrasenas validador;
    private final PDFReporte pdfReporte;
    private final PDFPrecios pdfPrecios;
    private final PDFTransacciones pdfTrans;

    public MenuConsola(BaseDatosLocal base, GestorArchivos gestorArchivos, EncriptadorAES encriptador, GeneradorContrasenas generador, ValidadorContrasenas validador, PDFReporte pdfReporte, PDFPrecios pdfPrecios, PDFTransacciones pdfTrans) {
        this.base = base;
        this.gestorArchivos = gestorArchivos;
        this.encriptador = encriptador;
        this.generador = generador;
        this.validador = validador;
        this.pdfReporte = pdfReporte;
        this.pdfPrecios = pdfPrecios;
        this.pdfTrans = pdfTrans;
    }

    public void mostrarMenu() {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("=======================================");
            System.out.println("        GESTOR DE CONTRASEÑAS");
            System.out.println("=======================================");
            System.out.println("1. Generar contraseña automática");
            System.out.println("2. Guardar contraseña manual");
            System.out.println("3. Mostrar contraseñas guardadas");
            System.out.println("4. Generar PDF Reporte");
            System.out.println("5. Generar PDF Precios");
            System.out.println("6. Generar PDF Transacciones");
            System.out.println("7. Abrir interfaz gráfica");
            System.out.println("0. Salir");
            System.out.print("Seleccione opción: ");

            String opt = sc.nextLine().trim();
            switch (opt) {
                case "1" -> opcionGenerarAutomatica(sc);
                case "2" -> opcionGuardarManual(sc);
                case "3" -> opcionMostrar();
                case "4" -> opcionGenerarPDFReporte();
                case "5" -> opcionGenerarPDFPrecios();
                case "6" -> opcionGenerarPDFTransacciones();
                case "7" -> VentanaPrincipal.mostrar(base, gestorArchivos, encriptador);
                case "0" -> salir = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void opcionGenerarAutomatica(Scanner sc) {
        System.out.print("Longitud deseada (ej: 12): ");
        int longitud = leerEntero(sc, 12);
        String pass = generador.generar(longitud);
        double precio = ListaPrecios.calcularPrecio(longitud);
        Contrasena c = new Contrasena(UUID.randomUUID().toString(), "AUTOMATICA", "usuario@local", pass, longitud, precio);
        base.getContrasenas().add(c);
        base.getTransacciones().add(new com.proyectofinal17.modelo.Transaccion("Generacion automatica", precio));
        System.out.println("Contraseña generada: " + pass);
        System.out.printf("Precio: $%.2f%n", precio);
        // Guardar en archivo cifrado
        try {
            gestorArchivos.guardar(base.getContrasenas(), encriptador);
            System.out.println("Guardado en almacenamiento cifrado.");
        } catch (Exception e) {
            System.out.println("Error guardando: " + e.getMessage());
        }
    }

    private void opcionGuardarManual(Scanner sc) {
        System.out.print("Nombre o servicio: ");
        String servicio = sc.nextLine().trim();
        System.out.print("Usuario/Email: ");
        String usuario = sc.nextLine().trim();
        System.out.print("Contraseña: ");
        String pass = sc.nextLine();
        if (!validador.esSegura(pass)) {
            System.out.println("Advertencia: contraseña no cumple criterios de seguridad recomendados.");
        }
        int longitud = pass.length();
        double precio = ListaPrecios.calcularPrecio(longitud);
        Contrasena c = new Contrasena(UUID.randomUUID().toString(), servicio, usuario, pass, longitud, precio);
        base.getContrasenas().add(c);
        base.getTransacciones().add(new com.proyectofinal17.modelo.Transaccion("Ingreso manual", precio));
        System.out.printf("Contraseña guardada. Precio: $%.2f%n", precio);
        try {
            gestorArchivos.guardar(base.getContrasenas(), encriptador);
            System.out.println("Guardado en almacenamiento cifrado.");
        } catch (Exception e) {
            System.out.println("Error guardando: " + e.getMessage());
        }
    }

    private void opcionMostrar() {
        List<Contrasena> lista = base.getContrasenas();
        if (lista.isEmpty()) {
            System.out.println("(vacío)");
            return;
        }
        System.out.println("--- Lista de contraseñas (no mostrar en ambientes inseguros) ---");
        for (Contrasena c : lista) {
            System.out.printf("%s | %s | usuario: %s | len=%d | precio=$%.2f%n",
                    c.getId(), c.getNombre(), c.getUsuario(), c.getLongitud(), c.getPrecio());
        }
    }

    private void opcionGenerarPDFReporte() {
        pdfReporte.generar("reporte_contrasenas.pdf", base.getContrasenas());
        System.out.println("PDF de reporte generado (reporte_contrasenas.pdf).");
    }

    private void opcionGenerarPDFPrecios() {
        pdfPrecios.generar("tabla_precios.pdf");
        System.out.println("PDF de precios generado (tabla_precios.pdf).");
    }

    private void opcionGenerarPDFTransacciones() {
        pdfTrans.generar("transacciones.pdf", base.getTransacciones());
        System.out.println("PDF de transacciones generado (transacciones.pdf).");
    }

    private int leerEntero(Scanner sc, int defecto) {
        try {
            String in = sc.nextLine().trim();
            if (in.isEmpty()) return defecto;
            return Integer.parseInt(in);
        } catch (NumberFormatException e) {
            return defecto;
        }
    }
}
