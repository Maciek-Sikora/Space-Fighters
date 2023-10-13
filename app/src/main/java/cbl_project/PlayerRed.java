package cbl_project;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;


public class PlayerRed {

    GamePanel gp;
    KeyHandler keyH;
    BufferedImage playerSpirit = null;
    Graphics2D playerGraphic;
    int width;
    int height ;
    int x= 300;
    int y= 250;
    int speed = 5;

    public PlayerRed(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        
        setUp();
    }

    public BufferedImage rotateImage(BufferedImage img, double degrees){
        double theta = Math.toRadians(degrees);

        AffineTransform tx = new AffineTransform();
        tx.rotate(theta, img.getWidth() / 2, img.getHeight() / 2);

        AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
        return op.filter(img, null);
    }

    void setUp(){
        try {
            playerSpirit = ImageIO.read(getClass().getResourceAsStream("/spaceship_red.png"));
            playerSpirit = rotateImage(playerSpirit, 270);

            System.out.println("[INFO] Player templates downloaded succesfuly");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("[ERROR] Image load failure!");
        }
    }
    void update(){
        if(keyH.w && y - speed >=0){
            y -= speed;
        }
        if(keyH.s && y + speed + height < gp.getHeight()){
            y+=speed;
        }
        if(keyH.a && x - speed >0){
            x-= speed;
        }
        if(keyH.d && x + speed + width + 10 < gp.getWidth()/2 - gp.spaceBetweenBorders){
            x+=speed;
        }
    }
    void draw(Graphics2D g2){
        width = gp.getWidth()/15;
        height = gp.getHeight()/15;
        g2.drawImage(playerSpirit, x,y, width,height,null);
    } 

}
