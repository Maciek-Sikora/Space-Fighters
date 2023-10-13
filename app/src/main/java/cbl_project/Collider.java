package cbl_project;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Collider {
    List<Bullet> bullets;
    Bullet bullet;
    // TODO: DEL
    Graphics2D g2;

    void checkBulletCoolision(PlayerRed playerRed){
        int i =0;
        while(i< bullets.size()){
            bullet = bullets.get(i);
            if(bullet.ownerId == playerRed.id){
                i++;
                continue;
            }
            Rectangle bulletRectangle = new Rectangle(bullet.x, bullet.y, bullet.x + bullet.width, bullet.y + bullet.height);
            Rectangle playerRectangle = new Rectangle(playerRed.x, playerRed.y, playerRed.x + playerRed.width ,playerRed.y + playerRed.height);
            if(bulletRectangle.intersects(playerRectangle)){
                bullets.remove(i);
                continue;
            }
            i++;
        }
    }

    void checkBulletCoolision(PlayerYellow playerYellow){
        int i =0;
        while(i< bullets.size()){
            bullet = bullets.get(i);
            if(bullet.ownerId == playerYellow.id){
                i++;
                continue;
            }
            Rectangle bulletRectangle = new Rectangle(bullet.x, bullet.y, bullet.x + bullet.width, bullet.y + bullet.height);
            Rectangle playerRectangle = new Rectangle(playerYellow.x,playerYellow.y,playerYellow.x + playerYellow.width ,playerYellow.y + playerYellow.height);
            if(bulletRectangle.intersects(playerRectangle)){
                bullets.remove(i);
                continue;
            }
            i++;
        }
    }
}
