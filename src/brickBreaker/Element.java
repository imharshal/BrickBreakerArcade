package brickBreaker;

import java.awt.Image;
import java.awt.Rectangle;
/* This is Element class which contain all the graphics details like width height and gettter setter methods
 */
public class Element {
    /*
    These attributes are protected so that all the subclasses can use them freely
     */
    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected Image image;
    protected int width;
    protected int height;
    /*
    Implementing two constructors setting up initial values for easy initialisation
     */
    public Element() {
        x = y = 0;
        dx = dy = 0;
        width = height = 0;
        image = null;
    }
    public Element(int x, int y) {
        this.x = x;
        this.y = y;
        dx = dy = 0;
        width = height = 0;
        image = null;
    }
    // Setter and getter methods for the X attribute
    public void setX(int x) { this.x = x; }
    public int getX() { return x; }

    // Setter and getter methods for the Y attribute
    public void setY(int y) { this.y = y; }
    public int getY() { return y; }

    //Setter and getter methods for the dx attribute
    public void setDx(int dx) { this.dx = dx; }
    public int getDx() { return dx; }

    // Setter and getter methods for the dy attribute
    public void setDy(int dy) { this.dy = dy; }
    public int getDy() { return dy; }

    /* Setter and getter methods for image attribute
     */
    public void setImage(Image img) {
        image = img;
        width = image.getWidth(null);
        height = image.getHeight(null);
    }
    public Image getImage() {
        return image;
    }

    // Getter methods for height
    public int getHeight() {
        return height;
    }

    // Getter method for width
    public int getWidth() {
        return width;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }
}
