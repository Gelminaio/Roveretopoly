/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package roveretopoly;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author GelmiRusso
 */

public class Box extends JPanel {

    private BufferedImage image;
    private int angle;

    public Box(int x, int y, int angoloRotazione, String nomeImmagine, int position) {
        setOpaque(false);
        setLayout(new BorderLayout());

        image = loadImage("Immagini/Caselle/" + nomeImmagine + ".png");
        angle = angoloRotazione;

        if (angle == 90 || angle == 270) {
            setBounds(x, y, 125, 75);
        } else {
            setBounds(x, y, 75, 125);
        }

        JButton button = new JButton();
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Board.updatePropertyInfo(String.valueOf(position));
            }
        });

        add(button, BorderLayout.CENTER);
    }

    private BufferedImage loadImage(String filePath) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(filePath));
            int targetWidth = 0;
            int targetHeight = 0;
            if (angle == 90 || angle == 270) {
                targetWidth = 125;
                targetHeight = 75;
            } else {
                targetWidth = 75;
                targetHeight = 125;
            }

            Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);

            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            return resizedImage; 
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int width = image.getWidth();
        int height = image.getHeight();
        double radianAngle = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(radianAngle));
        double cos = Math.abs(Math.cos(radianAngle));
        int newWidth = (int) Math.floor(width * cos + height * sin);
        int newHeight = (int) Math.floor(height * cos + width * sin);

        int x = (getWidth() - newWidth) / 2;
        int y = (getHeight() - newHeight) / 2;

        AffineTransform transform = new AffineTransform();
        transform.translate(x, y);
        transform.rotate(radianAngle, width / 2.0, height / 2.0);
        if (angle == 90) {
            transform.translate(((newHeight - height) / 2), -(newWidth - width) / 2);
        } else if (angle == 180) {
            transform.translate(newWidth - width, newHeight - height);
        } else if (angle == 270) {
            transform.translate(-(newHeight - height) / 2, (newWidth - width) / 2);
        }
        g2d.drawImage(image, transform, null);
        g2d.dispose();
    }
}
