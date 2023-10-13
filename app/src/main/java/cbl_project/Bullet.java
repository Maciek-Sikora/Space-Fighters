package cbl_project;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;


public class Bullet {

    GamePanel gp;
    KeyHandler keyH;
    BufferedImage[] bulletsSpirits = new BufferedImage[4];

    int width = 10;
    int height = 17;
    int x;
    int y;
    int speed = 5;
    int angle;
    int spiritCounter=0;
    int spiritNumber =0;
    int ownerId;

    public Bullet(GamePanel gp, KeyHandler keyH, int xStart, int yStart, int angleStart, int ownerId) {
        this.gp = gp;
        this.keyH = keyH;
        x = xStart;
        y = yStart;
        angle = angleStart;
        this.ownerId = ownerId;
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
            bulletsSpirits[0] = ImageIO.read(getClass().getResourceAsStream("/bullet1.png"));
            bulletsSpirits[0] = rotateImage(bulletsSpirits[0], angle);
            bulletsSpirits[1] = ImageIO.read(getClass().getResourceAsStream("/bullet2.png"));
            bulletsSpirits[1] = rotateImage(bulletsSpirits[1], angle);
            bulletsSpirits[2] = ImageIO.read(getClass().getResourceAsStream("/bullet3.png"));
            bulletsSpirits[2] = rotateImage(bulletsSpirits[2], angle);
            bulletsSpirits[3] = ImageIO.read(getClass().getResourceAsStream("/bullet4.png"));
            bulletsSpirits[3] = rotateImage(bulletsSpirits[3], angle);

            System.out.println("[INFO] Bullet templates downloaded succesfuly");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("[ERROR] Image load failure!");
        }
    }
    void update(){
        x += Math.sin(Math.toRadians(angle))*speed;
        y += Math.cos(Math.toRadians(angle))*speed;

        spiritCounter++;
        if(spiritCounter>10){
            spiritNumber = (spiritNumber+1 )%4;
            spiritCounter = 0;
        }
    }
    void draw(Graphics2D g2){
        g2.drawImage(bulletsSpirits[spiritNumber], null, x, y);
    } 

}
