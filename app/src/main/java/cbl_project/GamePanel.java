package cbl_project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    final int screenWidth = 3840;
    final int screenHeight = 2160;

    int FPS = 60;
    Thread gameThread;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

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
                System.out.println(drawCount);
                drawCount =0;
                timer = 0;
            }
            
        }
    }

    public void update(){

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;


    }
}
