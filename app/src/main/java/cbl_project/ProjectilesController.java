package cbl_project;

import java.awt.Graphics2D;
import java.util.*;

public class ProjectilesController {
    List<Bullet> bullets = new ArrayList<Bullet>();
    GamePanel gp;
    KeyHandler keyH;
    Collider collider;
    public ProjectilesController(GamePanel gp, KeyHandler keyH, Collider collider) {
        this.gp = gp;
        this.keyH = keyH;
        collider.bullets = this.bullets;
        this.collider = collider;
    }

    void addBullet(GamePanel gp, KeyHandler keyH, int xStart, int yStart, int angleStart, int ownerId){
        bullets.add(new Bullet(gp, keyH, xStart, yStart, angleStart, ownerId));
    }
    

    void updateProjectiles(){
        for(Bullet bullet: bullets){
            bullet.update();
        }
    }
    
    void drawProjectiles(Graphics2D g2){
        for(Bullet bullet: bullets){
            bullet.draw(g2);
        }
    }
}
