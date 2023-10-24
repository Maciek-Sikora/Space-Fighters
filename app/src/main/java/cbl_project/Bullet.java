package cbl_project;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * The class handles bullets shot by spaceships.
 */
public class Bullet {
    GamePanel gp;
    KeyHandler keyH;
    BufferedImage[] bulletsSpirits = new BufferedImage[4];
    double yOffset;
    double xOffset;
    int x;
    int y;
    int speed = 5;
    int angle;
    int spiritCounter = 0;
    int spiritNumber = 0;
    int ownerId;
    int width = 10;
    int height = 10;
    
    /**
     * Initializes the bullet class.
     * @param gp The gamepanel class.
     * @param keyH Keyhandler, handles key inputs.
     * @param xStart The starting x position of the bullet.
     * @param yStart The starting y position of the bullet.
     * @param angleStart The starting angle of the bullet.
     * @param ownerId The ID of the spaceship that shot the bullet.
     */
    public Bullet(GamePanel gp, KeyHandler keyH,
        int xStart, int yStart, int angleStart, int ownerId) {
        this.gp = gp;
        this.keyH = keyH;
        x = xStart;
        y = yStart;
        angle = angleStart;
        this.ownerId = ownerId;
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
            e.printStackTrace();
            System.out.println("[ERROR] Image load failure!");
        }
    }
    boolean insideMap(){
        if(y< -100 || x <-100){
            return false;
        }
        if(x > gp.getWidth() + 100){
            return false;
        }
        if( y > gp.getHeight() + 100){
            return false;
        }
        return true;
    }

    /**
     * Updates the position and the animation of the bullet.
     */
    void update() {
        xOffset = Math.sin(Math.toRadians(angle)) * speed;
        yOffset = Math.cos(Math.toRadians(angle)) * speed;
        x += xOffset;
        y -= yOffset;

        spiritCounter++;
        if (spiritCounter > 10) {
            spiritNumber = (spiritNumber + 1) % 4;
            spiritCounter = 0;
        }
    }

    /**
     * Draws the bullet.
     * @param g2 The graphics2D object that draws the image.
     */
    void draw(Graphics2D g2) {
        g2.drawImage(bulletsSpirits[spiritNumber], null, x, y);
    } 

}
