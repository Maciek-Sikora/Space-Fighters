package cbl_project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    final int screenWidth = 1920*2/3;
    final int screenHeight = 1080*2/3;

    int FPS = 60;
    List<Opponent> opponents = new ArrayList<Opponent>();;
    KeyHandler keyH = new KeyHandler();
    MouseHandler mouseHandler = new MouseHandler();
    Collider collider = new Collider();
    ProjectilesController projectilesController = new ProjectilesController(this, keyH, collider);
    Thread gameThread;
    Menu menu = new Menu(this, keyH, mouseHandler);
    
    PlayerRed playerRed = new PlayerRed(this, keyH, projectilesController, collider);
    PlayerYellow playerYellow = new PlayerYellow(this, keyH, projectilesController, collider);
    
    long opponentTimer = 0;

    int spaceBetweenBordersRatio = 7;
    int spaceBetweenBorders = screenWidth/spaceBetweenBordersRatio;
    int idCounter = 2;
    
    enum GameState {
        MENU,
        GAME
    }

    GameState gameState = GameState.MENU;

    
    public GamePanel(){
        collider.gp = this;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.addMouseListener(mouseHandler);

    }
    void spawnOpponents(){
        if(opponentTimer>1000){
            System.out.println("P");
            this.opponents.add(new Opponent(this, keyH, projectilesController, collider,idCounter));
            opponentTimer = 0;
            idCounter+=1;
        }
        opponentTimer++;
        if(opponents.size() == 0){
            opponentTimer+=2;
        }
    }
    void deleteOpponent(Opponent opponent){
        for(int i =0; i < opponents.size(); i++){
            if(opponents.get(i) == opponent){
                opponents.remove(i);
                break;
            }
        }
    }

    void opponentsUpdate(){
        int i =0;
        while(i<opponents.size()){
            opponents.get(i).update();
            i++;
        }
    }
    void opponentsDraw(Graphics2D g2){
        for(Opponent opponent: opponents){
            opponent.draw(g2);
        }
    }


    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run(){
        double drawInterval = 1e9 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        // Variables for fps tracking only:
        long timer = 0;
        int drawCount =0;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) /drawInterval;
            timer += (currentTime - lastTime);

            lastTime = currentTime;

            if(delta >=1){
                update();

                repaint();
                delta --;

                drawCount++;
            }

            if(timer >= 1e9){
                System.out.println("[INFO] Running FPS="+drawCount);
                drawCount =0;
                timer = 0;
            }
            
        }
    }

    public void update(){
        spaceBetweenBorders = this.getWidth()/spaceBetweenBordersRatio;
        if (gameState == GameState.MENU) {
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
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        menu.draw(g2);
        if (gameState == GameState.MENU) {
            menu.drawStartMenu(g2);
        } else if (gameState == GameState.GAME) {
            playerRed.draw(g2);
            playerYellow.draw(g2);
            opponentsDraw(g2);
            projectilesController.drawProjectiles(g2);
        }
        g2.dispose();
    }
}
