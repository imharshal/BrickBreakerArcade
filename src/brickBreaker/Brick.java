package brickBreaker;

import javax.swing.ImageIcon;

//This Brick class extends Element, and represents the breakable tiles
public class Brick extends Element implements Common {
    public boolean destroyed;
    //The destroyed attribute indicates whether the current brick is destroyed or not

    public Brick(int x, int y) {
        super(x, y);
        setImage(new ImageIcon(
                this.getClass().getResource(Common.IMG_BRICK)).getImage());
        destroyed = false;
    }
}
