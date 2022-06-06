package brickBreaker;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.ImageIcon;

//The Paddle class extends Element class. It has functionality of the paddle, including event handling

public class Paddle extends Element implements Common {
    private KeyHandler keyHandler = null; //Instance of the key event handler
    private MouseHandler mouseHandler = null; // Instance of the mouse event handler

    public Paddle() {
        setImage(new ImageIcon(this.getClass().getResource(Common.IMG_PADDLE)).getImage());
        reset();
    }

    public  void move() {
        x += dx; // Update regular movement

        if(x <= -width/2) x = -width/2;
        if(x >= Common.WIDTH - width/2) x = Common.WIDTH - width/2;
    }

    public void reset(){
        x = Common.WIDTH / 3;
        y = Common.HEIGHT - 32;
    }

    //This inner class handles the Keyboard events and updates the Paddle
    class KeyHandler extends KeyAdapter {
        // Start moving when left or right keys are pressed
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT) dx = -2;
            if (key == KeyEvent.VK_RIGHT) dx = 2;
        }
        //stop moving when left or right keys are released
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT) dx = 0;
            if(key == KeyEvent.VK_RIGHT) dx = 0;
        }
    }
    //Getter method for the key handler
    public KeyHandler getKeyHandler(){
        if(keyHandler == null) keyHandler = new KeyHandler();
        return keyHandler;
    }

    //This inner class handles the mouse events and update the paddle.
    class MouseHandler extends MouseAdapter {
        @Override
        public void mouseMoved(MouseEvent e) {
            int mouseX = e.getX();
            //Set paddle position according to mouse position
            setX(mouseX - width / 2);
        }
    }
    //Getter method for the mouse handler
    public MouseHandler getMouseHandler() {
        if(mouseHandler == null) mouseHandler = new MouseHandler();
        return mouseHandler;
    }
}
