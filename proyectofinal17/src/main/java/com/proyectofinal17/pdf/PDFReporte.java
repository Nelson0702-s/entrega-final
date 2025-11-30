package com.proyectofinal17.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.proyectofinal17.modelo.Contrasena;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * PDFReporte: genera un PDF con la lista de contraseñas.
 */
public class PDFReporte {

    public void generar(String ruta, List<Contrasena> lista) {
        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();
            doc.add(new Paragraph("REPORTE DE CONTRASEÑAS"));
            doc.add(new Paragraph(" "));
            for (Contrasena c : lista) {
                doc.add(new Paragraph(c.toString()));
            }
            doc.close();
            // Abrir con la app por defecto (Adobe si es predeterminado)
            File f = new File(ruta);
            if (f.exists()) java.awt.Desktop.getDesktop().open(f);
        } catch (DocumentException | IOException e) {
            System.out.println("Error generando PDFReporte: " + e.getMessage());
            if (doc.isOpen()) doc.close();
        }
    }
}
