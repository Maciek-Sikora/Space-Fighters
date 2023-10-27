package cbl_project;

import java.awt.Graphics2D;
import java.util.*;

/**
 * The ProjectilesController class handles the bullets and the rockets.
 */
public class ProjectilesController {
    List<Bullet> bullets = new ArrayList<Bullet>();
    List<RocketToYellow> rocketsToYellow = new ArrayList<RocketToYellow>();
    List<RocketToRed> rocketsToRed = new ArrayList<RocketToRed>();
    GamePanel gp;
    KeyHandler keyH;
    Collider collider;
    int yellowBullets;
    int redBullets;

    /**
     * Initializes the ProjectilesController class.
     * @param gp The gamepanel object.
     * @param keyH The keyhandler.
     * @param collider Collider object.
     */

    public ProjectilesController(GamePanel gp, KeyHandler keyH, Collider collider) {
        this.gp = gp;
        this.keyH = keyH;
        collider.bullets = this.bullets;
        collider.rocketsToYellow = this.rocketsToYellow;
        collider.rocketsToRed = this.rocketsToRed;
        this.collider = collider;
    }

    /**
     * Adds a bullet to the memory. Play sound effect.
     * @param gp The gamepanel object.
     * @param keyH The keyhandler.
     * @param xStart Start x-coordinate of the bullet.
     * @param yStart Start y-coordinate of the bullet.
     * @param angleStart The initial angle of the rocket.
     * @param ownerId Owner's id.
     */
    void addBullet(GamePanel gp, KeyHandler keyH, int xStart, int yStart, 
        int angleStart, int ownerId) {
        switch (ownerId) {
            case 1:
                if (redBullets > 8) {
                    return;
                }
                gp.playSoundEffect(4);
                bullets.add(new Bullet(gp, keyH, xStart, yStart, angleStart, ownerId));
                redBullets++;
                break;
            case 2:
                if (yellowBullets > 8) {
                    return;
                }
                gp.playSoundEffect(4);
                bullets.add(new Bullet(gp, keyH, xStart, yStart, angleStart, ownerId));
                yellowBullets++;
                break;
            default:
                gp.playSoundEffect(4);
                bullets.add(new Bullet(gp, keyH, xStart, yStart, angleStart, ownerId));
                break;
        }
        
        
    }

    /**
     * Adds a rocket which is going to the red Player to the memory. Play sound effect.
     * @param gp The gamepanel object.
     * @param keyH The keyhandler.
     * @param xStart Start x-coordinate of the rocket.
     * @param yStart Start y-coordinate of the rocket.
     * @param angleStart The initial angle of the rocket.
     * @param ownerId Owner's id.
     */
    void redLaunchRocket(GamePanel gp, KeyHandler keyH, int xStart, 
        int yStart, int angleStart, int owner) {
        if (rocketsToYellow.size() > 2) {
            return;
        }
        gp.playSoundEffect(6);
        rocketsToYellow.add(new RocketToYellow(gp, keyH, xStart, yStart, angleStart, owner));
    }

    /**
     * Adds a rocket which is going to the yellow player to the memory. Play sound effect.
     * @param gp The gamepanel object.
     * @param keyH The keyhandler.
     * @param xStart Start x-coordinate of the rocket.
     * @param yStart Start y-coordinate of the rocket.
     * @param angleStart The initial angle of the rocket.
     * @param ownerId Owner's id.
     */
    void yellowLaunchRocket(GamePanel gp, KeyHandler keyH, 
        int xStart, int yStart, int angleStart, int owner) {
        if (rocketsToRed.size() > 2) {
            return;
        }
        gp.playSoundEffect(6);
        rocketsToRed.add(new RocketToRed(this.gp, this.keyH, xStart, yStart, angleStart, owner));
    }

    /**
     * Deletes projectiles out of the board.
     */
    void deleteProjectiles() {
        int i = 0;
        while (i < bullets.size()) {
            Bullet bullet = bullets.get(i);
            if (!bullet.insideMap()) {
                if (bullet.ownerId == 1) {
                    redBullets--;
                } else if (bullet.ownerId == 2) {
                    yellowBullets--;
                }
                bullets.remove(i);
                continue;
            }
            i++;
        }

        i = 0;
        while (i < rocketsToRed.size()) {
            if (!rocketsToRed.get(i).insideMap()) {
                rocketsToRed.remove(i);
                continue;
            }
            i++;
        } 

        i = 0;
        while (i < rocketsToYellow.size()) {
            if (!rocketsToYellow.get(i).insideMap()) {
                rocketsToYellow.remove(i);
                continue;
            }
            i++;
        } 
    }

    /**
     * Updates projectiles.
     */
    void updateProjectiles() {
        deleteProjectiles();
        for (Bullet bullet : bullets) {
            bullet.update();
        }
        for (RocketToRed rocket : rocketsToRed) {
            rocket.update();
        }
        for (RocketToYellow rocket : rocketsToYellow) {
            rocket.update();
        }
    }

    /**
     * Draws the player and HP bar.
     * @param g2 The graphics2D object that draws the image.
    */
    void drawProjectiles(Graphics2D g2) {
        for (Bullet bullet : bullets) {
            bullet.draw(g2);
        }
        for (RocketToRed rocket : rocketsToRed) {
            rocket.draw(g2);
        }
        for (RocketToYellow rocket : rocketsToYellow) {
            rocket.draw(g2);
        }
    }
}
