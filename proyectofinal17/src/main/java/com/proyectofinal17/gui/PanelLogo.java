package com.proyectofinal17.gui;

import javax.swing.*;
import java.awt.*;

/**
 * PanelLogo: muestra logo.png si existe en el working directory.
 */
public class PanelLogo extends JPanel {

    public PanelLogo() {
        setPreferredSize(new Dimension(120, 120));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            ImageIcon icon = new ImageIcon("logo.png"); // coloca logo.png en la raíz del proyecto
            Image img = icon.getImage();
            if (img != null) {
                int w = Math.min(getWidth(), img.getWidth(null));
                int h = Math.min(getHeight(), img.getHeight(null));
                g.drawImage(img, 10, 10, w, h, this);
            }
        } catch (Exception e) {
            // no hay logo -> no problema
        }
    }
}
