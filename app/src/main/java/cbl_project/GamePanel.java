package cbl_project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    final int screenWidth = 1920*2/3;
    final int screenHeight = 1080*2/3;

    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Menu menu = new Menu(this, keyH);
    PlayerRed playerRed = new PlayerRed(this, keyH);
    PlayerYellow playerYellow = new PlayerYellow(this, keyH);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

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
        playerRed.update();
        playerYellow.update();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        menu.draw(g2);
        playerRed.draw(g2);
        playerYellow.draw(g2);
        g2.dispose();
    }
}
