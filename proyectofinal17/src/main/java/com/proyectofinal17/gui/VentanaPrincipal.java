package com.proyectofinal17.gui;

import com.proyectofinal17.datos.BaseDatosLocal;
import com.proyectofinal17.datos.GestorArchivos;
import com.proyectofinal17.modelo.Contrasena;
import com.proyectofinal17.modelo.ListaPrecios;
import com.proyectofinal17.pdf.PDFPrecios;
import com.proyectofinal17.pdf.PDFReporte;
import com.proyectofinal17.pdf.PDFTransacciones;
import com.proyectofinal17.seguridad.EncriptadorAES;
import com.proyectofinal17.seguridad.GeneradorContrasenas;
import com.proyectofinal17.seguridad.ValidadorContrasenas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.UUID;

/**
 * VentanaPrincipal: interfaz gráfica con botones, colores y logo.
 * Permite generar contraseñas, guardarlas y crear los 3 PDFs.
 */
public class VentanaPrincipal extends JFrame {

    private final BaseDatosLocal base;
    private final GestorArchivos gestorArchivos;
    private final EncriptadorAES encriptador;
    private final GeneradorContrasenas generador;
    private final ValidadorContrasenas validador;
    private final PDFReporte pdfReporte;
    private final PDFPrecios pdfPrecios;
    private final PDFTransacciones pdfTrans;

    public VentanaPrincipal(BaseDatosLocal base,
                            GestorArchivos gestorArchivos,
                            EncriptadorAES encriptador) {
        super("Proyectofinal17 - Gestor de Contraseñas");
        this.base = base;
        this.gestorArchivos = gestorArchivos;
        this.encriptador = encriptador;
        this.generador = new GeneradorContrasenas();
        this.validador = new ValidadorContrasenas();
        this.pdfReporte = new PDFReporte();
        this.pdfPrecios = new PDFPrecios();
        this.pdfTrans = new PDFTransacciones();
        construirUI();
    }

    private void construirUI() {
        setSize(800, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(28, 80, 148));

        // Header con logo y título
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        PanelLogo logo = new PanelLogo();
        header.add(logo, BorderLayout.WEST);
        JLabel titulo = new JLabel("Gestor de Contraseñas - Proyectofinal17");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        header.add(titulo, BorderLayout.CENTER);
        root.add(header, BorderLayout.NORTH);

        // Centro con formulario
        JPanel centro = new JPanel();
        centro.setOpaque(false);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));

        JTextField tfServicio = new JTextField();
        tfServicio.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        tfServicio.setBorder(BorderFactory.createTitledBorder("Servicio / Nombre"));
        centro.add(tfServicio);

        JTextField tfUsuario = new JTextField();
        tfUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        tfUsuario.setBorder(BorderFactory.createTitledBorder("Usuario / Email"));
        centro.add(tfUsuario);

        JTextField tfContrasena = new JTextField();
        tfContrasena.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        tfContrasena.setBorder(BorderFactory.createTitledBorder("Contraseña"));
        centro.add(tfContrasena);

        JButton bGenerar = new JButton("Generar automática (12)");
        bGenerar.addActionListener(e -> {
            String pass = generador.generar(12);
            tfContrasena.setText(pass);
        });

        JButton bGuardar = new JButton("Guardar");
        bGuardar.addActionListener((ActionEvent e) -> {
            String servicio = tfServicio.getText().trim();
            String usuario = tfUsuario.getText().trim();
            String valor = tfContrasena.getText();
            if (valor == null || valor.isEmpty()) {
                JOptionPane.showMessageDialog(VentanaPrincipal.this, "Ingrese o genere una contraseña.");
                return;
            }
            if (!validador.esSegura(valor)) {
                int res = JOptionPane.showConfirmDialog(VentanaPrincipal.this, "Contraseña no cumple criterios de seguridad. ¿Desea guardar igual?", "Advertencia", JOptionPane.YES_NO_OPTION);
                if (res != JOptionPane.YES_OPTION) return;
            }
            int longitud = valor.length();
            double precio = ListaPrecios.calcularPrecio(longitud);
            Contrasena c = new Contrasena(UUID.randomUUID().toString(), servicio.isEmpty() ? "MANUAL" : servicio, usuario.isEmpty() ? "usuario@local" : usuario, valor, longitud, precio);
            base.getContrasenas().add(c);
            base.getTransacciones().add(new com.proyectofinal17.modelo.Transaccion("Ingreso manual GUI", precio));
            try {
                gestorArchivos.guardar(base.getContrasenas(), encriptador);
                JOptionPane.showMessageDialog(VentanaPrincipal.this, "Contraseña guardada. Precio: $" + precio);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(VentanaPrincipal.this, "Error guardando: " + ex.getMessage());
            }
        });

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 6));
        botones.setOpaque(false);
        botones.add(bGenerar);
        botones.add(bGuardar);

        centro.add(Box.createRigidArea(new Dimension(0,10)));
        centro.add(botones);

        // Botones PDF
        JButton bPdfR = new JButton("Generar PDF Reporte");
        bPdfR.addActionListener(e -> {
            pdfReporte.generar("reporte_contrasenas.pdf", base.getContrasenas());
        });

        JButton bPdfP = new JButton("Generar PDF Precios");
        bPdfP.addActionListener(e -> pdfPrecios.generar("tabla_precios.pdf"));

        JButton bPdfT = new JButton("Generar PDF Transacciones");
        bPdfT.addActionListener(e -> pdfTrans.generar("transacciones.pdf", base.getTransacciones()));

        JPanel pPdf = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 6));
        pPdf.setOpaque(false);
        pPdf.add(bPdfR);
        pPdf.add(bPdfP);
        pPdf.add(bPdfT);
        centro.add(Box.createRigidArea(new Dimension(0,6)));
        centro.add(pPdf);

        root.add(centro, BorderLayout.CENTER);
        add(root);
    }

    // Método estático de conveniencia: abrir la ventana desde el menú
    public static void mostrar(BaseDatosLocal base, GestorArchivos gestor, EncriptadorAES encriptador) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal v = new VentanaPrincipal(base, gestor, encriptador);
            v.setVisible(true);
        });
    }

    // Si se quiere usar una instancia sin parámetros
    public static void mostrar() {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal v = new VentanaPrincipal(new BaseDatosLocal(), new GestorArchivos("storage_contrasenas.dat"), new EncriptadorAES());
            v.setVisible(true);
        });
    }
}
