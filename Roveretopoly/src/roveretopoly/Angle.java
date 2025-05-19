/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package roveretopoly;

import javax.imageio.ImageIO;  // Importa la classe ImageIO per leggere le immagini
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author GelmiRusso
 */

public class Angle extends JPanel {

    private BufferedImage image;  // Variabile per memorizzare l'immagine

    public Angle(int x, int y, String nomeImmagine) {
        setOpaque(false);  // Imposta la trasparenza del pannello
        setLayout(new BorderLayout());

        image = loadImage("Immagini/Angolo/" + nomeImmagine + ".png");  // Carica l'immagine da file

        // Imposta le dimensioni del pannello in base all'angolo di rotazione
        setBounds(x, y, 125, 125);

        JButton button = new JButton();  // Crea un nuovo pulsante
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        add(button, BorderLayout.CENTER);  // Aggiunge il pulsante al centro del pannello
    }

    // Metodo per caricare un'immagine da file
    private BufferedImage loadImage(String filePath) {
        try {
            // Legge l'immagine originale dal file
            BufferedImage originalImage = ImageIO.read(new File(filePath));

            int targetWidth = 125;  // Larghezza target dell'immagine ridimensionata
            int targetHeight = 125;  // Altezza target dell'immagine ridimensionata

            // Ridimensiona l'immagine originale alla dimensione desiderata
            Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);

            // Crea una nuova immagine ridimensionata
            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            return resizedImage;  // Restituisce l'immagine ridimensionata
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.drawImage(image, 0, 0, null);

        g2d.dispose();
    }
}

