package cbl_project;

import javax.imageio.ImageIO;

import cbl_project.GamePanel.GameState;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.Rectangle;
import java.awt.Color;

/**
 * The PlayerYellow class handles the spaceship on the right side of the screen (Yellow player).
 */
public class PlayerYellow {

    GamePanel gp;
    KeyHandler keyH;
    BufferedImage playerSpirit = null;
    Graphics2D playerGraphic;
    int width;
    int height;
    int x = 1000;
    int y = 300;
    int speed = 5;
    int id = 2;

    ProjectilesController projectilesController;
    Collider collider;

    int hp = 100;    
    Rectangle leftHpRectangle = new Rectangle();
    Rectangle rightHpRectangle = new Rectangle();

    /**
     * Initializes the PlayerYellow class.
     * @param gp The gamepanel object.
     * @param keyH The keyhandler.
     * @param projectilesController The object that controls the projectiles.
     * @param collider Collider object.
     */
    public PlayerYellow(GamePanel gp, KeyHandler keyH, 
        ProjectilesController projectilesController, Collider collider) {
        this.gp = gp;
        this.keyH = keyH;
        this.collider = collider;
        this.projectilesController = projectilesController;
        setUp();
    }

    /**
     * Rotates an image.
     * @param img The image
     * @param degrees The degree by which the image has to be rotated.
     * @return The rotated image.
     */
    public BufferedImage rotateImage(BufferedImage img, double degrees) {
        double theta = Math.toRadians(degrees);

        AffineTransform tx = new AffineTransform();
        tx.rotate(theta, img.getWidth() / 2, img.getHeight() / 2);

        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(img, null);
    }

    /**
     * Reads the image files, prints error in case of an exception.
     */
    void setUp() {
        try {
            playerSpirit = ImageIO.read(getClass().getResourceAsStream("/spaceship_yellow.png"));
            playerSpirit = rotateImage(playerSpirit, 90);

            System.out.println("[INFO] Player templates downloaded succesfuly");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[ERROR] Image load failure!");
        }
    }

    /**
     * Checks if any bullet or rocket is coliding with the spaceship.
     */
    void checkColision() {
        collider.checkBulletCollision(this);
        collider.checkRocketCollisionYellow();
    }

    /**
     * Handles the movement of the player. There is also bullet and missile launch bind.
     */
    void movement() {
        if (keyH.up && y - speed >= 0) {
            y -= speed;
        }
        if (keyH.down && y + speed + height < gp.getHeight()) {
            y += speed;
        }
        if (keyH.right && x + speed + width < gp.getWidth()) {
            x += speed;
        }
        if (keyH.left && x - speed > gp.getWidth() / 2 + gp.spaceBetweenBorders) {
            x -= speed;
        }

        if (keyH.rightShift) {
            projectilesController.addBullet(gp, keyH, x, y + height / 2 - 10, 270, id);
            keyH.rightShift = false;
        }
        if (keyH.rightCtr) {
            projectilesController.yellowLaunchRocket(gp, keyH, x, y + height / 2 - 10, 270,2);
            keyH.rightCtr = false;
        }
    }

    /**
     * Makes an HP bar below the player.
     */
    void hpBar() {
        leftHpRectangle = new Rectangle(x, y + height + 5, width * hp / 100, height / 20);
        rightHpRectangle = new Rectangle(x + width * hp / 100, y + height + 5, 
        width - (width * hp / 100), height / 20);
    }

    /**
     * Updates the player. Change the gamestate after death.
     */
    void update() {
        movement();
        if (hp < 0) {
            hp = 0;
            gp.sound.playEndMusic();
            gp.gameState = GameState.END;
            gp.winner = "Red";
        }
        hpBar();
        checkColision();
    }
    
    /**
     * Draws the player and HP bar.
     * @param g2 The graphics2D object that draws the image.
     */
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
    }

}
