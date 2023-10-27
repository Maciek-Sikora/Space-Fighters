package cbl_project;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The class controls and handles the keys.
 */
public class KeyHandler implements KeyListener {

    // Check if key is pressed:
    public boolean w;
    public boolean s;
    public boolean a; 
    public boolean d;
    public boolean leftCtr;
    public boolean leftShift;
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    public boolean rightCtr;
    public boolean rightShift;

    /**
    * The class controls and handles the keys and the states of the keys.
    * @param e KeyEvent object that has typed keys.
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
    * The class controls and handles the keys and the states of the keys.
    * @param e KeyEvent object that has pressed keys.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            w = true;
        }
        if (code == KeyEvent.VK_S) {
            s = true;
        }
        if (code == KeyEvent.VK_A) {
            a = true;
        }
        if (code == KeyEvent.VK_D) {
            d = true;
        }

        if (code == KeyEvent.VK_UP) {
            up = true;
        }
        if (code == KeyEvent.VK_DOWN) {
            down = true;
        }
        if (code == KeyEvent.VK_RIGHT) {
            right = true;
        }
        if (code == KeyEvent.VK_LEFT) {
            left = true;
        }


        if (code == KeyEvent.VK_SHIFT) {
            if (e.getKeyLocation() == 2) {
                leftShift = true;
            } 
            if (e.getKeyLocation() == 3) {
                rightShift = true;
            }
        }

        if (code == KeyEvent.VK_CONTROL) {
            if (e.getKeyLocation() == 2) {
                leftCtr = true;
            } 
            if (e.getKeyLocation() == 3) {
                rightCtr = true;
            }
        }

    }

    /**
    * The class controls and handles the keys and the states of the keys.
    * @param e KeyEvent object that has released keys.
    */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            w = false;
        }
        if (code == KeyEvent.VK_S) {
            s = false;
        }
        if (code == KeyEvent.VK_A) {
            a = false;
        }
        if (code == KeyEvent.VK_D) {
            d = false;
        }

        if (code == KeyEvent.VK_UP) {
            up = false;
        }
        if (code == KeyEvent.VK_DOWN) {
            down = false;
        }
        if (code == KeyEvent.VK_RIGHT) {
            right = false;
        }
        if (code == KeyEvent.VK_LEFT) {
            left = false;
        }

        if (code == KeyEvent.VK_SHIFT) {
            if (e.getKeyLocation() == 2) {
                leftShift = false;
            } 
            if (e.getKeyLocation() == 3) {
                rightShift = false;
            }
        }

        if (code == KeyEvent.VK_CONTROL) {
            if (e.getKeyLocation() == 2) {
                leftCtr = false;
            } 
            if (e.getKeyLocation() == 3) {
                rightCtr = false;
            }
        }
        
    }

}