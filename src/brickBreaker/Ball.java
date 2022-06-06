package brickBreaker;

import javax.swing.ImageIcon;

//The Ball class extends Element, represents the ball object
public class Ball extends Element implements Common {
    public Ball() {
        super();
        // Set initial moving direction towards bottom-right
        dx = 2;
        dy = 2;
        setImage(new ImageIcon(
                this.getClass().getResource(Common.IMG_BALL)).getImage());
        // Put the ball in the initial position
        reset();
    }
    /* Method to update ball's movement. Bounces the bass back when it hits
     * the screen borders
     */
    public void move() {
        //Update movement.
        x += dx;
        y += dy;
        //Bounce ball back when it hits borders
        if(x <= 0) {
            x = 0;
            dx = -dx; // Reverse the horizontal direction
        } else if (x >= Common.WIDTH - width) {
            x = Common.WIDTH - width;
            dx = -dx; // Reverse the horizontal direction
        }
        if (y <= 0) {
            y = 0;
            dy = -dy; // Reverse the vertical direction.
        }
    }

    private void reset() {
        x = Common.WIDTH / 3;
        y = Common.HEIGHT / 2;
    }

}
