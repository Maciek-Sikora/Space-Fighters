package cbl_project;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.Rectangle;
import java.awt.Color;

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

    public Opponent(GamePanel gp, KeyHandler keyH, ProjectilesController projectilesController, Collider collider) {
        this.gp = gp;
        this.keyH = keyH;
        this.projectilesController = projectilesController;
        this.collider = collider;
        x = gp.getWidth() /2 - width/2;

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

    void update() {
        movement();
        hpBar();

        if(timer == 200){
            timer=0;
            projectilesController.redLaunchRocket(gp, keyH, x,y + height/2,270);
            projectilesController.yellowLaunchRocket(gp, keyH, x + width, y + height/2, 90);
        }
        timer++;
    }

    void draw(Graphics2D g2) {
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
