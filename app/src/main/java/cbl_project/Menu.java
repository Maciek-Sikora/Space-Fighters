package cbl_project;


import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Menu {
    GamePanel gp;
    KeyHandler keyH;
    BufferedImage backgroundImage = null;
    int width = 300;
    int height = 250;

    public Menu(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setUp();
    }
    void setUp(){
        try {
            // backgroundImage = ImageIO.read(new FileInputStream("templates/space.png"));
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/space.png"));
            System.out.println("[INFO] Menu templates downloaded succesfuly");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("[ERROR] Image load failure!");
        }
    }
    void update(){
        
    }
    void draw(Graphics2D g2){
        width = gp.getWidth();
        height = gp.getHeight();
        g2.drawImage(backgroundImage, 0,0, width, height,null);
        // Boarder
        g2.setStroke(new BasicStroke(20f));
        g2.drawLine(width/2 - gp.spaceBetweenBorders, 0, width/2 - gp.spaceBetweenBorders, height);
        g2.drawLine(width/2 + gp.spaceBetweenBorders, 0, width/2 + gp.spaceBetweenBorders, height);
        g2.setStroke(new BasicStroke(3f));
    }

}
