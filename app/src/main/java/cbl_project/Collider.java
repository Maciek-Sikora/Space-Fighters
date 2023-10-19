package cbl_project;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Collider {
    List<Bullet> bullets;
    List<RocketToYellow> rocketsToYellow;
    List<RocketToRed> rocketsToRed;
    GamePanel gp;
    Bullet bullet;
    RocketToRed rocketToRed;
    RocketToYellow rocketToYellow;


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

    void checkRocketCoolisionRed(){
        int i =0;
        int dist = 0;
        while(i< rocketsToRed.size()){
            rocketToRed = rocketsToRed.get(i);
            double x1 = rocketToRed.x;
            double y1 = rocketToRed.y;
            double x2 = gp.playerRed.x + gp.playerRed.width/2;
            double y2 = gp.playerRed.y + gp.playerRed.height/2;
            dist = (int)Point2D.distance(x1,y1,x2,y2);
            if(dist<rocketToRed.rocketDetonationDist){
                rocketsToRed.remove(i);
                gp.playerRed.hp -= 10;
                continue;
            }
            i++;
        }
    }

    void checkRocketCoolisionYellow(){
        int i =0;
        int dist = 0;
        while(i< rocketsToYellow.size()){
            rocketToYellow = rocketsToYellow.get(i);
            double x1 = rocketToYellow.x;
            double y1 = rocketToYellow.y;
            double x2 = gp.playerYellow.x + gp.playerYellow.width/2;
            double y2 = gp.playerYellow.y + gp.playerYellow.height/2;
            dist = (int)Point2D.distance(x1,y1,x2,y2);
            if(dist<rocketToYellow.rocketDetonationDist){
                rocketsToYellow.remove(i);
                gp.playerYellow.hp -= 10;
                continue;
            }
            i++;
        }
    }
    void checkOpponentBulletCollisioN(Opponent opponent){
        int i =0;
        while(i< bullets.size()){
            bullet = bullets.get(i);
            if(bullet.ownerId == opponent.id){
                i++;
                continue;
            }
            Rectangle bulletRectangle = new Rectangle(bullet.x, bullet.y, bullet.width, bullet.height);
            Rectangle playerRectangle = new Rectangle(opponent.x,opponent.y,opponent.width , opponent.height);
            if(bulletRectangle.intersects(playerRectangle)){
                bullets.remove(i);
                opponent.hp -= 10;
                if(opponent.hp<=0){
                    gp.deleteOpponent(opponent);
                    return;
                }
                continue;
            }
            i++;
        }
    }
    void checkOpponentCoolisionRocketsToYellow(Opponent opponent){
        int i =0;
        int dist = 0;
        while(i< rocketsToYellow.size()){
            rocketToYellow = rocketsToYellow.get(i);
             if(rocketToYellow.ownerId == opponent.id){
                i++;
                continue;
            }
            double x1 = rocketToYellow.x;
            double y1 = rocketToYellow.y;
            double x2 = opponent.x + opponent.width/2;
            double y2 = opponent.y + opponent.height/2;
            dist = (int)Point2D.distance(x1,y1,x2,y2);
            if(dist<rocketToYellow.rocketDetonationDist){
                rocketsToYellow.remove(i);
                opponent.hp -= 20;
                if(opponent.hp<=0){
                    gp.deleteOpponent(opponent);
                    return;
                }
                continue;
            }
            i++;
        }

    }
    void checkOpponentCoolisionRocketsToRed(Opponent opponent){
        int i =0;
        int dist = 0;
        while(i< rocketsToRed.size()){
            rocketToRed = rocketsToRed.get(i);
            if(rocketToRed.ownerId == opponent.id){
                i++;
                continue;
            }
            double x1 = rocketToRed.x;
            double y1 = rocketToRed.y;
            double x2 = opponent.x + opponent.width/2;
            double y2 = opponent.y + opponent.height/2;
            dist = (int)Point2D.distance(x1,y1,x2,y2);
            if(dist<rocketToRed.rocketDetonationDist){
                rocketsToRed.remove(i);
                opponent.hp -= 20;
                if(opponent.hp<=0){
                    gp.deleteOpponent(opponent);
                    return;
                }
                continue;
            }
            i++;
        }
    }
    void checkOpponentCollision(Opponent opponent){
        
        checkOpponentBulletCollisioN(opponent);
        checkOpponentCoolisionRocketsToRed(opponent);
        checkOpponentCoolisionRocketsToYellow(opponent);
        
        
    }
}
