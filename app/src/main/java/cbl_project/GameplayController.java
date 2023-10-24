package cbl_project;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * The class controls the window of the game.
 */
public class GameplayController {
    public static JFrame window;

    /**
     * Sets the window properties and starts the game.
     */
    void startGame() {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        
        window.setTitle("Space fighters");
        try{
            BufferedImage icon = ImageIO.read(getClass().getResourceAsStream("/spaceship_red.png"));
            window.setIconImage(icon);
            System.out.println("[INFO] Icon templates downloaded succesfuly");
        }catch(Exception e){}
        

        window.setLocationByPlatform(true);


        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
