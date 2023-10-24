package cbl_project;

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
        window.setResizable(true);
        
        window.setTitle("Space fighters");
        
        window.setLocationByPlatform(true);


        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
