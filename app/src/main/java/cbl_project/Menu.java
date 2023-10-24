package cbl_project;

import cbl_project.GamePanel.GameState;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * The menu class. Includes methods for drawing the start menu.
 */
public class Menu {
    GamePanel gp;
    KeyHandler keyH;
    MouseHandler mouseHandler;
    BufferedImage backgroundImage = null;
    BufferedImage winnerImage = null;
    BufferedImage helpMenuImage = null;
    int width = 300;
    int height = 250;
    double scaling = 1;
    public Rectangle startButton;
    public Rectangle quitButton;
    public Rectangle helpButton;
    public Rectangle backButton;
    public Rectangle menuBackground;

    /**
     * Initializes the menu class.
     * @param gp The gamepanel class.
     * @param keyH Keyhandler, handles key inputs.
     * @param mouseHandler Handles mouse inputs.
     */
    public Menu(GamePanel gp, KeyHandler keyH, MouseHandler mouseHandler) {
        this.gp = gp;
        this.keyH = keyH;
        this.mouseHandler = mouseHandler;
        setUp();
    }

    /**
     * Reads the image files for the menu and background.
     */
    void setUp() {
        try {
            // backgroundImage = ImageIO.read(new FileInputStream("templates/space.png"));
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/space.png"));
            helpMenuImage = ImageIO.read(getClass().getResourceAsStream("/help_menu.png"));
            System.out.println("[INFO] Menu templates downloaded succesfuly");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR] Image load failure!");
        }
    }

    /**
     * Updates the scaling of the menu depending on window size. Handles button presses.
     */
    void update() {
        width = gp.getWidth();
        height = gp.getHeight();
        scaling = (double) height / 720;

        int scaledButtonOffsetY = (int) (100 * scaling);
        int scaledButtonHeight = (int) (75 * scaling);
        int scaledBackButtonSize = (int) (50 * scaling);

        startButton = new Rectangle(width / 3, height / 4 + (int) (scaledButtonOffsetY / 2.3),
            width / 3, scaledButtonHeight);
        quitButton = new Rectangle(width / 3, startButton.y + scaledButtonOffsetY,
            width / 3, scaledButtonHeight);
        helpButton = new Rectangle(width / 3, quitButton.y + scaledButtonOffsetY,
            width / 3, scaledButtonHeight);
        backButton = new Rectangle(width / 4 + 1, height / 4 + 1,
            scaledBackButtonSize, scaledBackButtonSize);
        menuBackground = new Rectangle(width / 4, height / 4, width / 2, height / 2);

        if (gp.gameState == GameState.MENU) {
            if (startButton.contains(mouseHandler.x, mouseHandler.y)) {
                gp.stopMusic();
                gp.playSoundEffect(2);
                gp.playMusic(1);
                gp.gameState = GameState.GAME;
            } else if (helpButton.contains(mouseHandler.x, mouseHandler.y)) {
                gp.playSoundEffect(2);
                gp.gameState = GameState.HELP;
            } else if (quitButton.contains(mouseHandler.x, mouseHandler.y)) {
                gp.playSoundEffect(2);
                System.exit(0);
            }
        } else if (gp.gameState == GameState.HELP) {
            if (backButton.contains(mouseHandler.x, mouseHandler.y)) {
                gp.playSoundEffect(2);
                gp.gameState = GameState.MENU;
            }
        } else if (gp.gameState == GameState.END) {
            if (backButton.contains(mouseHandler.x, mouseHandler.y)) {
                gp.stopMusic();
                gp.playSoundEffect(2);
                gp.playMusic(0);
                gp.gameState = GameState.MENU;
                gp.resetGame();
            }
        }
    }

    /**
     * Draws the end screen menu.
     * @param g2 The graphics2D object that draws the image.
     */
    void drawEndScreen(Graphics2D g2) {
        g2.setStroke(new BasicStroke(5f));
        g2.setColor(Color.decode("#000080"));
        g2.draw(menuBackground);
        g2.setColor(Color.decode("#000033"));
        g2.fill(menuBackground);

        g2.setColor(Color.decode("#000080"));
        g2.draw(backButton);
        g2.setColor(Color.decode("#000066"));
        g2.fill(backButton);

        int scaledTextOffsetX = backButton.width / 2 - (int) (20 * scaling);
        int scaledTextOffsetY = backButton.height / 2 + (int) (3 * scaling);

        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.BOLD, (int) (15 * scaling)));
        g2.drawString("Back", backButton.x + scaledTextOffsetX, backButton.y + scaledTextOffsetY);
        
        scaledTextOffsetX = -(int) ((75 + 5 * gp.winner.length()) * scaling);
        scaledTextOffsetY = (int) (75 * scaling);

        g2.setFont(new Font("Arial", Font.BOLD, (int) (40 * scaling)));
        g2.drawString(gp.winner + " won",
            width / 2 + scaledTextOffsetX, height / 2 + scaledTextOffsetY);

        if (gp.winner.equals("Yellow")) {
            winnerImage = gp.playerYellow.playerSpirit;
        } else {
            winnerImage = gp.playerRed.playerSpirit;
        }

        int winnerWidth = (int) (winnerImage.getWidth() / 5 * scaling);
        int winnerHeight = (int) (winnerImage.getHeight() / 5 * scaling);
        int scaledImageOffsetY = (int) (winnerHeight / 3 * scaling);

        g2.drawImage(winnerImage,
            width / 2 - winnerWidth / 2, height / 2 - winnerHeight / 2 - scaledImageOffsetY,
            winnerWidth, winnerHeight, null);
    }

    /**
     * 
     * @param g2
     */
    void drawHelpMenu(Graphics2D g2) {
        g2.setStroke(new BasicStroke(5f));
        g2.setColor(Color.decode("#000080"));
        g2.draw(menuBackground);
        g2.setColor(Color.decode("#000033"));
        g2.fill(menuBackground);
        g2.drawImage(helpMenuImage, menuBackground.x, menuBackground.y,
            menuBackground.width, menuBackground.height, null);

        g2.setColor(Color.decode("#000080"));
        g2.draw(backButton);
        g2.setColor(Color.decode("#000066"));
        g2.fill(backButton);

        int scaledTextOffsetX = backButton.width / 2 - (int) (20 * scaling);
        int scaledTextOffsetY = backButton.height / 2 + (int) (3 * scaling);

        g2.setColor(Color.white);
        g2.setFont(new Font("Arial", Font.BOLD, (int) (15 * scaling)));
        g2.drawString("Back", backButton.x + scaledTextOffsetX, backButton.y + scaledTextOffsetY);
    }

    /**
     * 
     * @param g2
     */
    void drawStartMenu(Graphics2D g2) {
        g2.setStroke(new BasicStroke(5f));
        g2.setColor(Color.decode("#000080"));
        g2.draw(menuBackground);
        g2.setColor(Color.decode("#000033"));
        g2.fill(menuBackground);

        g2.setColor(Color.decode("#000080"));
        g2.draw(startButton);
        g2.draw(quitButton);
        g2.draw(helpButton);
        g2.setColor(Color.decode("#000066"));
        g2.fill(startButton);
        g2.fill(quitButton);
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

    /**
     * 
     * @param g2
     */
    void draw(Graphics2D g2) {
        g2.drawImage(backgroundImage, 0, 0, width, height, null);
        // Boarder
        g2.setStroke(new BasicStroke(20f));
        g2.drawLine(width / 2 - gp.spaceBetweenBorders, 0,
            width / 2 - gp.spaceBetweenBorders, height);
        g2.drawLine(width / 2 + gp.spaceBetweenBorders, 0,
            width / 2 + gp.spaceBetweenBorders, height);
        g2.setStroke(new BasicStroke(3f));
    }

}
