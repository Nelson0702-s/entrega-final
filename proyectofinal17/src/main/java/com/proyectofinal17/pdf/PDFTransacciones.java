package com.proyectofinal17.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.proyectofinal17.modelo.Transaccion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * PDFTransacciones: genera un PDF con el historial de transacciones.
 */
public class PDFTransacciones {

    public void generar(String ruta, List<Transaccion> lista) {
        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();
            doc.add(new Paragraph("HISTORIAL DE TRANSACCIONES"));
            doc.add(new Paragraph(" "));
            double total = 0;
            for (Transaccion t : lista) {
                doc.add(new Paragraph(t.toString()));
                total += t.getMonto();
            }
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("TOTAL FACTURADO: $" + total));
            doc.close();
            File f = new File(ruta);
            if (f.exists()) java.awt.Desktop.getDesktop().open(f);
        } catch (DocumentException | IOException e) {
            System.out.println("Error generando PDFTransacciones: " + e.getMessage());
            if (doc.isOpen()) doc.close();
        }
    }
}
