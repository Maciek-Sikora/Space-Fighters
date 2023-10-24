package cbl_project;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Handles mouse inputs. Changes the x and y value when the mouse is pressed.
 */
public class MouseHandler implements MouseListener {
    public int x;
    public int y;

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        x = 0;
        y = 0;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}