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

public class PlayerRed {

    GamePanel gp;
    KeyHandler keyH;
    BufferedImage playerSpirit = null;
    Graphics2D playerGraphic;
    int width;
    int height;
    int x = 300;
    int y = 250;
    int speed = 5;
    int id = 1;
    ProjectilesController projectilesController;
    Collider collider;
    int hp = 100;
    Rectangle leftHpRectangle;
    Rectangle rightHpRectangle;

    public PlayerRed(GamePanel gp, KeyHandler keyH, ProjectilesController projectilesController, Collider collider) {
        this.gp = gp;
        this.keyH = keyH;
        this.projectilesController = projectilesController;
        this.collider = collider;
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
            playerSpirit = ImageIO.read(getClass().getResourceAsStream("/spaceship_red.png"));
            playerSpirit = rotateImage(playerSpirit, 270);

            System.out.println("[INFO] Player templates downloaded succesfuly");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("[ERROR] Image load failure!");
        }
    }

    void checkColision() {
        collider.checkBulletCoolision(this);
        collider.checkRocketCoolisionRed();
    }

    void movement() {
        if (keyH.w && y - speed >= 0) {
            y -= speed;
        }
        if (keyH.s && y + speed + height < gp.getHeight()) {
            y += speed;
        }
        if (keyH.a && x - speed > 0) {
            x -= speed;
        }
        if (keyH.d && x + speed + width + 10 < gp.getWidth() / 2 - gp.spaceBetweenBorders) {
            x += speed;
        }

        if (keyH.leftShift) {
            projectilesController.addBullet(gp, keyH, x + width, y + height / 2 - 10, 90, id);
            keyH.leftShift = false;
        }
        if (keyH.leftCtr) {
            projectilesController.redLaunchRocket(gp, keyH, x + width, y + height / 2 - 10, 90, 1);
            keyH.leftCtr = false;
        }
    }

    void hpBar() {
        leftHpRectangle = new Rectangle(x, y + height + 5, width * hp / 100, height / 20);
        rightHpRectangle = new Rectangle(x + width * hp / 100, y + height + 5, width - (width * hp / 100), height / 20);
    }

    void update() {
        movement();
        hpBar();
        checkColision();
    }

    void draw(Graphics2D g2) {
        width = gp.getWidth() / 15;
        height = gp.getHeight() / 15;
        g2.drawImage(playerSpirit, x, y, width, height, null);
        if (hp != 0) {
            g2.setColor(Color.GREEN);
            g2.draw(leftHpRectangle);
        }
        if (hp != 100) {
            g2.setColor(Color.RED);
            g2.draw(rightHpRectangle);
        }

        // g2.drawRect(x, y, width, height);

    }

}
