package cbl_project;


import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Menu {
    GamePanel gp;
    KeyHandler keyH;
    BufferedImage backgroundImage = null;

    public Menu(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setUp();
    }
    void setUp(){
        try {
            // backgroundImage = ImageIO.read(new FileInputStream("templates/space.png"));
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/space.png"));
            
            System.out.println("Menu templates downloaded succesfuly");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("[ERROR] Image load failure!");
        }
    }
    void update(){
        
    }
    void draw(Graphics2D g2){
        g2.drawImage(backgroundImage, 0,0, gp.getWidth(),gp.getHeight(),null);
    }

}
