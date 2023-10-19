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
    double scaling = 1;
    public Rectangle startButton;
    public Rectangle quitButton;
    public Rectangle helpButton;
    public Rectangle backButton;

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

    void update() {
        width = gp.getWidth();
        height = gp.getHeight();
        scaling = (double) height / 720;

        int scaledButtonOffsetY = (int) (100 * scaling);
        int scaledButtonHeight = (int) (75 * scaling);
        int scaledBackButtonSize = (int) (50 * scaling);

        startButton = new Rectangle(width / 3, height / 4 + scaledButtonOffsetY / 2,
            width / 3, scaledButtonHeight);
        quitButton = new Rectangle(width / 3, startButton.y + scaledButtonOffsetY,
            width / 3, scaledButtonHeight);
        helpButton = new Rectangle(width / 3, quitButton.y + scaledButtonOffsetY,
            width / 3, scaledButtonHeight);
        backButton = new Rectangle(width / 4 + 1, height / 4 + 1,
            scaledBackButtonSize, scaledBackButtonSize);

        if (gp.gameState == GameState.MENU) {
            if (startButton.contains(mouseHandler.x, mouseHandler.y)) {
                gp.gameState = GameState.GAME;
            } else if (helpButton.contains(mouseHandler.x, mouseHandler.y)) {
                gp.gameState = GameState.HELP;
            } else if (quitButton.contains(mouseHandler.x, mouseHandler.y)) {
                System.exit(0);
            }
        } else if (gp.gameState == GameState.HELP) {
            if (backButton.contains(mouseHandler.x, mouseHandler.y)) {
                gp.gameState = GameState.MENU;
            }
        } else if (gp.gameState == GameState.END) {
            if (backButton.contains(mouseHandler.x, mouseHandler.y)) {
                gp.gameState = GameState.MENU;
                gp.resetGame();
            }
        }
    }

    void drawEndScreen(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fill(new Rectangle(width / 4, height / 4, width / 2, height / 2));

        g2.setColor(Color.gray);
        g2.draw(backButton);
        g2.fill(backButton);

        int scaledTextOffsetX = backButton.width / 2 - (int) (20 * scaling);
        int scaledTextOffsetY = backButton.height / 2 + (int) (3 * scaling);

        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.BOLD, (int) (15 * scaling)));
        g2.drawString("Back", backButton.x + scaledTextOffsetX, backButton.y + scaledTextOffsetY);

        g2.setColor(Color.black);
        g2.setFont(new Font("Arial", Font.BOLD, (int) (40 * scaling)));
        g2.drawString(gp.winner + " won", width / 2, height / 2);
    }

    void drawHelpMenu(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fill(new Rectangle(width / 4, height / 4, width / 2, height / 2));

        g2.setColor(Color.gray);
        g2.draw(backButton);
        g2.fill(backButton);

        int scaledTextOffsetX = backButton.width / 2 - (int) (20 * scaling);
        int scaledTextOffsetY = backButton.height / 2 + (int) (3 * scaling);

        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.BOLD, (int) (15 * scaling)));
        g2.drawString("Back", backButton.x + scaledTextOffsetX, backButton.y + scaledTextOffsetY);
    }

    void drawStartMenu(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.fill(new Rectangle(width / 4, height / 4, width / 2, height / 2));

        g2.setColor(Color.gray);
        g2.draw(startButton);
        g2.fill(startButton);
        g2.draw(quitButton);
        g2.fill(quitButton);
        g2.draw(helpButton);
        g2.fill(helpButton);

        int scaledTextOffsetX = startButton.width / 2 - (int) (40 * scaling);
        int scaledTextOffsetY = startButton.height / 2 + (int) (10 * scaling);

        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.BOLD, (int) (30 * scaling)));
        g2.drawString("Start", startButton.x + scaledTextOffsetX,
            startButton.y + scaledTextOffsetY);
        g2.drawString("Quit", quitButton.x + scaledTextOffsetX,
            quitButton.y + scaledTextOffsetY);
        g2.drawString("Help", helpButton.x + scaledTextOffsetX,
            helpButton.y + scaledTextOffsetY);
    }

    void draw(Graphics2D g2){
        g2.drawImage(backgroundImage, 0,0, width, height,null);
        // Boarder
        g2.setStroke(new BasicStroke(20f));
        g2.drawLine(width/2 - gp.spaceBetweenBorders, 0, width/2 - gp.spaceBetweenBorders, height);
        g2.drawLine(width/2 + gp.spaceBetweenBorders, 0, width/2 + gp.spaceBetweenBorders, height);
        g2.setStroke(new BasicStroke(3f));
    }

}
