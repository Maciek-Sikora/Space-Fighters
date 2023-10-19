package cbl_project;


import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cbl_project.GamePanel.GameState;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Menu {
    GamePanel gp;
    KeyHandler keyH;
    MouseHandler mouseHandler;
    BufferedImage backgroundImage = null;
    int width = 300;
    int height = 250;
    public Rectangle startButton;
    public Rectangle quitButton;
    public Rectangle helpButton;

    public Menu(GamePanel gp, KeyHandler keyH, MouseHandler mouseHandler) {
        this.gp = gp;
        this.keyH = keyH;
        this.mouseHandler = mouseHandler;
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
        if (startButton != null) {
            if (startButton.contains(mouseHandler.x, mouseHandler.y)) {
                gp.gameState = GameState.GAME;
            } else if (quitButton.contains(mouseHandler.x, mouseHandler.y)) {
                System.exit(1);
            }
        }
    }

    void drawStartMenu(Graphics2D g2) {
        width = gp.getWidth();
        height = gp.getHeight();
        g2.setColor(Color.white);
        g2.fill(new Rectangle(width / 4, height / 4, width / 2, height / 2));

        g2.setColor(Color.gray);
        startButton = new Rectangle(width / 3, height / 4 + 50, width / 3, 75);
        quitButton = new Rectangle(width / 3, startButton.y + 100, width / 3, 75);
        helpButton = new Rectangle(width / 3, quitButton.y + 100, width / 3, 75);
        g2.draw(startButton);
        g2.fill(startButton);
        g2.draw(quitButton);
        g2.fill(quitButton);
        g2.draw(helpButton);
        g2.fill(helpButton);

        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.drawString("Start", startButton.x + startButton.width / 2 - 40,
            startButton.y + startButton.height / 2 + 10);
        g2.drawString("Quit", quitButton.x + quitButton.width / 2 - 40,
            quitButton.y + quitButton.height / 2 + 10);
        g2.drawString("Help", helpButton.x + helpButton.width / 2 - 40,
            helpButton.y + helpButton.height / 2 + 10);
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
