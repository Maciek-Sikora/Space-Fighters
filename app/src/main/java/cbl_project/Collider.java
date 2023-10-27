package cbl_project;

import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * The Collider class handles collisions between bullets, rockets and players.
 */
public class Collider {
    List<Bullet> bullets;
    List<RocketToYellow> rocketsToYellow;
    List<RocketToRed> rocketsToRed;
    GamePanel gp;
    Bullet bullet;
    RocketToRed rocketToRed;
    RocketToYellow rocketToYellow;

    /**
     * Checks if bullets collide with the red player.
     * @param playerRed The object PlayerRed.
     */
    void checkBulletCollision(PlayerRed playerRed) {
        int i = 0;
        while (i < bullets.size()) {
            bullet = bullets.get(i);
            if (bullet.ownerId == playerRed.id) {
                i++;
                continue;
            }
            Rectangle bulletRectangle = new Rectangle(bullet.x, bullet.y,
                bullet.width, bullet.height);
            Rectangle playerRectangle = new Rectangle(playerRed.x, playerRed.y,
                playerRed.width, playerRed.height);
            if (bulletRectangle.intersects(playerRectangle)) {
                bullets.remove(i);
                playerRed.hp -= 10;
                continue;
            }
            i++;
        }
    }

    /**
     * Checks if bullets collide with the yellow player.
     * @param playerYellow The object PlayerYellow.
     */
    void checkBulletCollision(PlayerYellow playerYellow) {
        int i = 0;
        while (i < bullets.size()) {
            bullet = bullets.get(i);
            if (bullet.ownerId == playerYellow.id) {
                i++;
                continue;
            }
            Rectangle bulletRectangle = new Rectangle(bullet.x, bullet.y,
                bullet.width, bullet.height);
            Rectangle playerRectangle = new Rectangle(playerYellow.x, playerYellow.y,
                playerYellow.width, playerYellow.height);
            if (bulletRectangle.intersects(playerRectangle)) {
                bullets.remove(i);
                playerYellow.hp -= 10;
                continue;
            }
            i++;
        }
    }

    /**
     * Checks if rockets collide with the red player.
     */
    void checkRocketCollisionRed() {
        int i = 0;
        int dist = 0;
        while (i < rocketsToRed.size()) {
            rocketToRed = rocketsToRed.get(i);
            double x1 = rocketToRed.x;
            double y1 = rocketToRed.y;
            double x2 = gp.playerRed.x + gp.playerRed.width / 2;
            double y2 = gp.playerRed.y + gp.playerRed.height / 2;
            dist = (int) Point2D.distance(x1, y1, x2, y2);
            if (dist < rocketToRed.rocketDetonationDist) {
                rocketsToRed.remove(i);
                gp.playSoundEffect(5);
                gp.playerRed.hp -= 11;
                continue;
            }
            i++;
        }
    }

    /**
     * Checks if rockets collide with the yellow player.
     */
    void checkRocketCollisionYellow() {
        int i = 0;
        int dist = 0;
        while (i < rocketsToYellow.size()) {
            rocketToYellow = rocketsToYellow.get(i);
            double x1 = rocketToYellow.x;
            double y1 = rocketToYellow.y;
            double x2 = gp.playerYellow.x + gp.playerYellow.width / 2;
            double y2 = gp.playerYellow.y + gp.playerYellow.height / 2;
            dist = (int) Point2D.distance(x1, y1, x2, y2);
            if (dist < rocketToYellow.rocketDetonationDist) {
                rocketsToYellow.remove(i);
                gp.playSoundEffect(5);
                gp.playerYellow.hp -= 11;
                continue;
            }
            i++;
        }
    }

    /**
     * Checks if bullets collide with the opponent.
     * @param opponent The opponent object.
     */
    void checkOpponentBulletCollision(Opponent opponent) {
        int i = 0;
        while (i < bullets.size()) {
            bullet = bullets.get(i);
            if (bullet.ownerId == opponent.id) {
                i++;
                continue;
            }
            Rectangle bulletRectangle = new Rectangle(bullet.x, bullet.y,
                bullet.width, bullet.height);
            Rectangle playerRectangle = new Rectangle(opponent.x, opponent.y,
                opponent.width, opponent.height);
            if (bulletRectangle.intersects(playerRectangle)) {
                bullets.remove(i);
                opponent.hp -= 11;
                if (opponent.hp <= 0) {
                    gp.deleteOpponent(opponent);
                    return;
                }
                continue;
            }
            i++;
        }
    }

    /**
     * Checks if the rockets shot by the opponent collides with the yellow player.
     * @param opponent The opponent object.
     */
    void checkOpponentCollisionRocketsToYellow(Opponent opponent) {
        int i = 0;
        int dist = 0;
        while (i < rocketsToYellow.size()) {
            rocketToYellow = rocketsToYellow.get(i);
            if (rocketToYellow.ownerId == opponent.id) {
                i++;
                continue;
            }
            double x1 = rocketToYellow.x;
            double y1 = rocketToYellow.y;
            double x2 = opponent.x + opponent.width / 2;
            double y2 = opponent.y + opponent.height / 2;
            dist = (int) Point2D.distance(x1, y1, x2, y2);
            if (dist < rocketToYellow.rocketDetonationDist) {
                rocketsToYellow.remove(i);
                opponent.hp -= 21;
                if (opponent.hp <= 0) {
                    gp.deleteOpponent(opponent);
                    return;
                }
                continue;
            }
            i++;
        }
    }

    /**
     * Checks if the rockets shot by the opponent collides with the red player.
     * @param opponent The opponent object.
     */
    void checkOpponentCoolisionRocketsToRed(Opponent opponent) {
        int i = 0;
        int dist = 0;
        while (i < rocketsToRed.size()) {
            rocketToRed = rocketsToRed.get(i);
            if (rocketToRed.ownerId == opponent.id) {
                i++;
                continue;
            }
            double x1 = rocketToRed.x;
            double y1 = rocketToRed.y;
            double x2 = opponent.x + opponent.width / 2;
            double y2 = opponent.y + opponent.height / 2;
            dist = (int) Point2D.distance(x1, y1, x2, y2);
            if (dist < rocketToRed.rocketDetonationDist) {
                rocketsToRed.remove(i);
                opponent.hp -= 21;
                if (opponent.hp <= 0) {
                    gp.deleteOpponent(opponent);
                    return;
                }
                continue;
            }
            i++;
        }
    }

    /**
     * Checks collisions for the specified opponent.
     * @param opponent The opponent object.
     */
    void checkOpponentCollision(Opponent opponent) {
        checkOpponentBulletCollision(opponent);
        checkOpponentCoolisionRocketsToRed(opponent);
        checkOpponentCollisionRocketsToYellow(opponent);
    }
}
