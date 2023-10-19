package cbl_project;

import javax.annotation.MatchesPattern;
import javax.imageio.ImageIO;

import com.google.common.base.Objects;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class RocketToRed {
    GamePanel gp;
    KeyHandler keyH;
    BufferedImage[] rocketSpirits = new BufferedImage[3];
    double yOffset, xOffset;
    int x,y;
    
    int width = 70;
    int height = 50;
    double angle;
    int spiritCounter=0;
    int spiritNumber =0;
    int speed = 5;
    double maxTiltAngle = 0.3;
    int rocketDetonationDist = 100;
    int ownerId;

    public RocketToRed(GamePanel gp, KeyHandler keyH, int xStart, int yStart, int angleStart, int ownerId) {
        this.gp = gp;
        this.keyH = keyH;
        this.x = xStart;
        this.y = yStart;
        this.angle = angleStart;
        this.ownerId = ownerId;
        setUp();
    }

    public BufferedImage rotateImage(BufferedImage img, double degrees){
        double theta = Math.toRadians(degrees);

        AffineTransform tx = new AffineTransform();
        tx.rotate(theta, img.getWidth() / 2, img.getHeight() / 2);

        AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BICUBIC);
        return op.filter(img, null);
    }

    void setUp(){
        try {
            rocketSpirits[0] = ImageIO.read(getClass().getResourceAsStream("/rocket1.png"));
            rocketSpirits[1] = ImageIO.read(getClass().getResourceAsStream("/rocket2.png"));
            rocketSpirits[2] = ImageIO.read(getClass().getResourceAsStream("/rocket3.png"));



            System.out.println("[INFO] Rocket templates downloaded succesfuly");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("[ERROR] Image load failure!");
        }
    }
    public static double getAngleOfLineBetweenTwoPoints(Point.Double p1, Point.Double p2)
    {
        double xDiff = p2.x - p1.x;
        double yDiff = p2.y - p1.y;
        return Math.toDegrees(Math.atan2(yDiff, xDiff));
    }
    void update(){
        Point.Double p1 = new Point.Double(-x,y);
        Point.Double p2 = new Point.Double(-gp.playerRed.x, gp.playerRed.y);
        double tilt = 270 - getAngleOfLineBetweenTwoPoints(p1, p2);
        double tiltDiff = tilt - angle;

        if(Math.abs(tiltDiff ) < maxTiltAngle){
            angle += tiltDiff;
        }else if(tiltDiff > 0){
            angle += maxTiltAngle;
        }else if (tiltDiff < 0){
            angle -= maxTiltAngle;
        }

        xOffset = Math.sin(Math.toRadians(angle))*speed;
        yOffset = Math.cos(Math.toRadians(angle))*speed;

        x += xOffset;
        y -= yOffset;

        spiritCounter++;
        if(spiritCounter>10){
            spiritNumber = (spiritNumber+1 )%3;
            spiritCounter = 0;
        }
    }

    void draw(Graphics2D g2){

        // g2.drawImage(rotateImage(rocketSpirits[spiritNumber], angle), x,y, width, height, null);
        // g2.translate(gp.WIDTH/2, gp.HEIGHT/2);
        g2.rotate(Math.toRadians(angle), x + rocketSpirits[spiritNumber].getWidth()/2, y + rocketSpirits[spiritNumber].getHeight()/2);
        g2.drawImage(rocketSpirits[spiritNumber], x,y, rocketSpirits[spiritNumber].getWidth()*3, rocketSpirits[spiritNumber].getHeight()*3, null);
        g2.rotate(-Math.toRadians(angle), x + rocketSpirits[spiritNumber].getWidth()/2, y + rocketSpirits[spiritNumber].getHeight()/2);
        // g2.translate(-x, -y);
        
        
    } 

}
