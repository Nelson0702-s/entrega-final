package com.proyectofinal17.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * PDFPrecios: genera un PDF con la tabla de precios.
 */
public class PDFPrecios {

    public void generar(String ruta) {
        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();
            doc.add(new Paragraph("TABLA DE PRECIOS - Proyectofinal17"));
            doc.add(new Paragraph("Longitud <= 6  -> $1.000"));
            doc.add(new Paragraph("7 - 10         -> $2.000"));
            doc.add(new Paragraph("11 - 15        -> $3.500"));
            doc.add(new Paragraph("> 15           -> $5.000"));
            doc.close();
            File f = new File(ruta);
            if (f.exists()) java.awt.Desktop.getDesktop().open(f);
        } catch (DocumentException | IOException e) {
            System.out.println("Error generando PDFPrecios: " + e.getMessage());
            if (doc.isOpen()) doc.close();
        }
    }
}
