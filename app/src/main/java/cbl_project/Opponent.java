package cbl_project;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.*;
import java.awt.geom.Point2D;

public class Opponent {

    GamePanel gp;
    KeyHandler keyH;
    BufferedImage playerSpirit = null;
    Graphics2D playerGraphic;
    int width;
    int height;
    int x = 300;
    int y = -50;
    int speed = 1;
    int id = 1;
    ProjectilesController projectilesController;
    Collider collider;
    int hp = 40;
    Rectangle leftHpRectangle;
    Rectangle rightHpRectangle;
    int timer = 0;

    public Opponent(GamePanel gp, KeyHandler keyH, ProjectilesController projectilesController, Collider collider, int id) {
        this.gp = gp;
        this.keyH = keyH;
        this.projectilesController = projectilesController;
        this.collider = collider;
        x = gp.getWidth() /2 - width/2;
        this.id = id;
        setUp();
    }

    public BufferedImage rotateImage(BufferedImage img, double degrees) {
        double theta = Math.toRadians(degrees);

        AffineTransform tx = new AffineTransform();
        tx.rotate(theta, img.getWidth() / 2, img.getHeight() / 2);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(img, null);
    }

    void setUp() {
        try {
            playerSpirit = ImageIO.read(getClass().getResourceAsStream("/opponentShip1.png"));
            playerSpirit = rotateImage(playerSpirit, 180);

            System.out.println("[INFO] Opponent templates downloaded succesfuly");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("[ERROR] Image load failure!");
        }
    }

    

    void movement() {
        x = gp.getWidth() /2 - width/2;
        y += speed;
    }

    void hpBar() {
        leftHpRectangle = new Rectangle(x, y + height + 5, width * hp / 40, height / 20);
        rightHpRectangle = new Rectangle(x + width * hp / 40, y + height + 5, width - (width * hp / 40), height / 20);
        
        
    }
    void checkColision(){
        collider.checkOpponentCollision(this);
        if(hp<=0) {
            gp.playSoundEffect(5);
            return;
        };
    }
    public static double getAngleOfLineBetweenTwoPoints(Point.Double p1, Point.Double p2)
    {
        double xDiff = p2.x - p1.x;
        double yDiff = p2.y - p1.y;
        return Math.toDegrees(Math.atan2(yDiff, xDiff));
    }
    void launchRockets(){
        if(timer == 100){
            projectilesController.addBullet(gp, keyH, x, y, 90, id);
            projectilesController.addBullet(gp, keyH, x, y, 270, id);
        }

        if(timer == 200){
            timer=0;

            Point.Double p1 = new Point.Double(x,-y);
            Point.Double p2 = new Point.Double(gp.playerYellow.x, -gp.playerYellow.y);
            double tilt = 90 - getAngleOfLineBetweenTwoPoints(p1, p2);
            projectilesController.redLaunchRocket(gp, keyH, x + width,y + height/2,(int)tilt,id);

            p1 = new Point.Double(-x,y);
            p2 = new Point.Double(-gp.playerRed.x, gp.playerRed.y);
            tilt = 270 - getAngleOfLineBetweenTwoPoints(p1, p2);
            projectilesController.yellowLaunchRocket(gp, keyH, x , y + height/2, (int) tilt,id);
        }
        timer++;
    }
    void deleteChecker(){
        if(y >= gp.getHeight()){
            gp.deleteOpponent(this);
        }
    }
    void update() {
        movement();
        hpBar();
        checkColision();
        if(hp<=0) {
            gp.playSoundEffect(5);
            return;
        };

        launchRockets();
        deleteChecker();

    }

    void draw(Graphics2D g2) {
        if(hp<=0) {
            gp.playSoundEffect(5);
            return;
        };
        width = gp.getWidth() / 15;
        height = gp.getHeight() / 15;
        g2.drawImage(playerSpirit, x, y, width, height, null);
        if (hp != 0) {
            g2.setColor(Color.GREEN);
            g2.draw(leftHpRectangle);
        }
        if (hp != 40) {
            g2.setColor(Color.RED);
            g2.draw(rightHpRectangle);
        }

        // g2.drawRect(x, y, width, height);

    }

}
