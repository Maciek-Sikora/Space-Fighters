package cbl_project;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Collider {
    List<Bullet> bullets;
    Bullet bullet;


    void checkBulletCoolision(PlayerRed playerRed){
        int i =0;
        while(i< bullets.size()){
            bullet = bullets.get(i);
            if(bullet.ownerId == playerRed.id){
                i++;
                continue;
            }
            // TODO: Change for tilted rectangles for Angles
            Rectangle bulletRectangle = new Rectangle(bullet.x, bullet.y, bullet.width, bullet.height);
            Rectangle playerRectangle = new Rectangle(playerRed.x, playerRed.y, playerRed.width , playerRed.height);
            if(bulletRectangle.intersects(playerRectangle)){
                bullets.remove(i);
                playerRed.hp -= 10;
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
            Rectangle bulletRectangle = new Rectangle(bullet.x, bullet.y, bullet.width, bullet.height);
            Rectangle playerRectangle = new Rectangle(playerYellow.x,playerYellow.y,playerYellow.width , playerYellow.height);
            if(bulletRectangle.intersects(playerRectangle)){
                bullets.remove(i);
                playerYellow.hp -= 10;
                continue;
            }
            i++;
        }
    }
}
