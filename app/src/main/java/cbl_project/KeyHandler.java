package cbl_project;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

    // Check if key is pressed:
    public boolean w,s,a,d;
    public boolean up,down, left, right;
    @Override
    public void keyTyped(KeyEvent e){

    }
    @Override
    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            w = true;
        }
        if(code == KeyEvent.VK_S){
            s = true;
        }
        if(code == KeyEvent.VK_A){
            a = true;
        }
        if(code == KeyEvent.VK_D){
            d = true;
        }

        if(code == KeyEvent.VK_UP){
            up = true;
        }
        if(code == KeyEvent.VK_DOWN){
            down = true;
        }
        if(code == KeyEvent.VK_RIGHT){
            right = true;
        }
        if(code == KeyEvent.VK_LEFT){
            left = true;
        }

    }
    @Override
    public void keyReleased(KeyEvent e){
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            w = false;
        }
        if(code == KeyEvent.VK_S){
            s = false;
        }
        if(code == KeyEvent.VK_A){
            a = false;
        }
        if(code == KeyEvent.VK_D){
            d = false;
        }

        if(code == KeyEvent.VK_UP){
            up = false;
        }
        if(code == KeyEvent.VK_DOWN){
            down = false;
        }
        if(code == KeyEvent.VK_RIGHT){
            right = false;
        }
        if(code == KeyEvent.VK_LEFT){
            left = false;
        }
    }
}
