package cbl_project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * The GamePanel class handles the game logic, plays sound, draws and updates all components.
 */
public class GamePanel extends JPanel implements Runnable {
    final int screenWidth = 1920 * 2 / 3;
    final int screenHeight = 1080 * 2 / 3;
    
    int fps = 60;
    Sound sound = new Sound();
    KeyHandler keyH = new KeyHandler();
    MouseHandler mouseHandler = new MouseHandler();
    Thread gameThread;
    Menu menu = new Menu(this, keyH, mouseHandler);

    List<Opponent> opponents = new ArrayList<Opponent>();
    Collider collider = new Collider();
    ProjectilesController projectilesController = new ProjectilesController(this, keyH, collider);
    PlayerRed playerRed = new PlayerRed(this, keyH, projectilesController, collider);
    PlayerYellow playerYellow = new PlayerYellow(this, keyH, projectilesController, collider);
    
    long opponentTimer = 0;
    int spaceBetweenBordersRatio = 7;
    int spaceBetweenBorders = screenWidth / spaceBetweenBordersRatio;
    int idCounter = 3;
    
    enum GameState {
        MENU,
        HELP,
        GAME,
        END
    }

    GameState gameState = GameState.MENU;
    String winner = "";

    /**
     * Resets the game to its initial position.
     */
    void resetGame() {
        opponents = new ArrayList<Opponent>();
        collider = new Collider();
        collider.gp = this;
        projectilesController = new ProjectilesController(this, keyH, collider);
        playerRed = new PlayerRed(this, keyH, projectilesController, collider);
        playerYellow = new PlayerYellow(this, keyH, projectilesController, collider);
    }
    
    /**
     * Initializes the GamePanel class.
     */
    public GamePanel() {
        resetGame();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.addMouseListener(mouseHandler);

    }

    /**
     * Spawns opponents if the timer reaches a certain point.
     */
    void spawnOpponents() {
        if (opponentTimer > 1000) {
            System.out.println("P");
            this.opponents.add(
                new Opponent(this, keyH, projectilesController, collider, idCounter));
            idCounter++;
            opponentTimer = 0;
        }
        opponentTimer++;
        if (opponents.size() == 0) {
            opponentTimer += 2;
        }
    }

    /**
     * Deletes an opponent.
     * @param opponent The opponent object that is to be deleted.
     */
    void deleteOpponent(Opponent opponent) {
        for (int i = 0; i < opponents.size(); i++) {
            if (opponents.get(i) == opponent) {
                opponents.remove(i);
                break;
            }
        }
    }

    /**
     * Updates all opponents.
     */
    void opponentsUpdate() {
        int i = 0;
        while (i < opponents.size()) {
            opponents.get(i).update();
            i++;
        }
    }

    /**
     * Draws all opponents.
     * @param g2 The graphics2D object that draws the image.
     */
    void opponentsDraw(Graphics2D g2) {
        for (Opponent opponent : opponents) {
            opponent.draw(g2);
        }
    }

    /**
     * Plays the specified music file.
     * @param i The index of the music file that is to be played.
     */
    void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    /**
     * Stops the music.
     */
    void stopMusic() {
        sound.stop();
    }

    /**
     * Plays the specified sound effect.
     * @param i The index of the sound effect.
     */
    void playSoundEffect(int i) {
        sound.setFile(i);
        sound.play();
    }

    /**
     * Starts the game thread.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run() {
        sound.playMenuMusic();
        double drawInterval = 1e9 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        // Variables for fps tracking only:
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);

            lastTime = currentTime;

            if (delta >= 1) {
                update();

                repaint();
                delta--;

                drawCount++;
            }

            if (timer >= 1e9) {
                System.out.println("[INFO] Running FPS=" + drawCount);
                drawCount = 0;
                timer = 0;
            }
            
        }
    }

    /**
     * Updates the game depending on what the gamestate is.
     */
    public void update() {
        spaceBetweenBorders = this.getWidth() / spaceBetweenBordersRatio;
        if (gameState == GameState.MENU || gameState == GameState.HELP
            || gameState == GameState.END) {
            menu.update();
        } else if (gameState == GameState.GAME) {
            playerRed.update();
            playerYellow.update();
            spawnOpponents();
            opponentsUpdate();
            projectilesController.updateProjectiles();
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        menu.draw(g2);
        if (gameState == GameState.MENU) {
            menu.drawStartMenu(g2);
        } else if (gameState == GameState.HELP) {
            menu.drawHelpMenu(g2);
        } else if (gameState == GameState.GAME) {
            playerRed.draw(g2);
            playerYellow.draw(g2);
            opponentsDraw(g2);
            projectilesController.drawProjectiles(g2);
        } else if (gameState == GameState.END) {
            menu.drawEndScreen(g2);
        }
        g2.dispose();
    }
}
