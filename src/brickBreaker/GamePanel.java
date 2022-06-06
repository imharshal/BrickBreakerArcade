package brickBreaker;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;
import java.util.Timer;

/* This is GamePanel class which extends JPanel.
This is where the graphics are drawn. It also has game loop and timer functionality,
which are responsible for updating the game objects and drawing them to panel

 */
public class GamePanel extends JPanel implements Common {
    // Variable declaration
    Timer timer;
    String message;
    Ball ball;
    Paddle paddle;
    Brick bricks[];
    int bricksRemaining;
    boolean playing = true;

    public GamePanel() {
        // Set Background colour
        setBackground(new Color(64, 64, 64, 64));
        //Enable double buffering of this panel to avoid flickering
        setDoubleBuffered(true);
        //set the focus to the GamePanel for keyboard events to work
        setFocusable(true);
        //Start up the game
        initGame();
    }

    private void initGame() {
        ball = new Ball(); // creating ball object
        paddle = new Paddle(); //creating paddle object
        //The paddle's even handlers are registered as event sources
        addKeyListener(paddle.getKeyHandler());
        addMouseMotionListener(paddle.getMouseHandler());

        // create bricks and arrange them
        bricks = new Brick[30];
        bricksRemaining = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                bricks[bricksRemaining++] = new Brick(40 + j * 40, 48 + i * 12);
            }
        }
        /* The timer object is used to schedule the game loop.
        scheduleAtFixedRate() is used to execute the GameLoopTask object
        The task runs at nearly 60 FPS. so 1000ms/60 = nearly 17 ms
         */
        timer = new Timer();
        timer.scheduleAtFixedRate(new GameLoopTask(), 1000, 17);
    }

    void stopGame() {
        playing = false;
        timer.cancel();
    }

    void handleCollisions() {
        //Handle collision with paddle
        if (ball.getRect().intersects(paddle.getRect())) {
            //play "bump" sound
            playSound(Common.SOUND_BUMP);
            //Reverse ball's Y direction
            ball.setDy(-ball.getDy());
            /*Change ball's dx based on which part of the paddle it hits
            the paddle has five zones, hitting the ball with each zone will
            move it in different angle, giving player more control.
             */
            int segment = paddle.getWidth() / 5;
            int first = paddle.getX() + segment;
            int second = paddle.getX() + segment * 2;
            int third = paddle.getX() + segment * 3;
            int fourth = paddle.getX() + segment * 4;
            int center = ball.getX() + ball.getWidth() / 2;

            if (center < first) {
                ball.setDx(-2);
            } else if (center >= first && center < second) {
                ball.setDx(-1);
            } else if (center >= second && center < third) {
                ball.setDx(0);
            } else if (center >= third && center < fourth) {
                ball.setDx(1);
            } else if (center > fourth) {
                ball.setDx(2);
            }
            //Reset ball's position out of collision
            ball.setY(paddle.getY() - ball.getHeight());
        }
        //Loop through the bricks array and handle collisions
        for (Brick brick : bricks) {
            //perform collision check only if brick is not destroyed
            // and collision occurs
            if ((!brick.destroyed) &&
                    (brick.getRect().intersects(ball.getRect()))) {
                playSound(Common.SOUND_PING);

                int top = ball.getY();
                int bottom = ball.getY() + ball.getHeight();
                int left = ball.getX();
                int right = ball.getX() + ball.getWidth();

                //set x direction depending on where collision occurs
                if (brick.getRect().contains(right + 1, top)) {
                    int dx = ball.getDx();
                    ball.setDx(dx < 0 ? dx : -dx); // ensure negative dx
                } else if (brick.getRect().contains(left - 1, top)) {
                    int dx = ball.getDx();
                    ball.setDx(dx < 0 ? -dx : dx); //ensure positive dx
                }
                //set Y direction depending on where collision occurs
                if (brick.getRect().contains(left, top - 1)) {
                    int dy = ball.getDy();
                    ball.setDy(dy < 0 ? -dy : dy); //ensure positive dy
                } else if (brick.getRect().contains(left, bottom + 1)) {
                    int dy = ball.getDy();
                    ball.setDy(dy < 0 ? dy : -dy); // ensure negative dy
                }
                //Destroy the brick and update brick count
                brick.destroyed = true;
                bricksRemaining--;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        /* Draw using a Graphics2D object because Graphics doesn't support
        anti-aliasing
         */
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        //If not lost or won (still playing), draw objects
        if (playing) {
            //Draw the ball
            g2d.drawImage(ball.getImage(), ball.getX(), ball.getY(),
                    ball.getWidth(), ball.getHeight(), this);
            //Draw the paddle
            g2d.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
                    paddle.getWidth(), paddle.getHeight(), this);

            //Draw the bricks
            for (Brick brick : bricks) {
                //If brick is destroyed, continue to next brick
                if (brick.destroyed) continue;
                //Else, draw the brick
                g2d.drawImage(brick.getImage(), brick.getX(), brick.getY(),
                        brick.getWidth(), brick.getHeight(), this);
            }
        }
        // if won or lost, display the message instead of the objects
        else {
            Font font = new Font("Arial", Font.ITALIC, 20);
            g2d.setColor(Color.WHITE);
            g2d.setFont(font);
            g2d.drawString(message,
                    Common.WIDTH / 2 -
                            this.getFontMetrics(font).stringWidth(message) / 2,
                    Common.HEIGHT / 2);
        }
        g2d.dispose();
    }

    class GameLoopTask extends TimerTask {
        /* This is where the game loop logic goes. Here, the objects are
        Updated and then drawn to the panel.
         */
        @Override
        public void run() {
            ball.move(); // Update ball's position
            paddle.move(); // update the paddle's position

            //if ball goes below screen, lose game
            if (ball.getY() > Common.HEIGHT) {
                message = "Game Over";
                stopGame();
            }
            //Else if all bricks are destroyed, win game.
            else if (bricksRemaining == 0) {
                message = "Congratulations! You won!";
                stopGame();
            }
            //Detect and handle collision between different objects
            handleCollisions();
            //Render objects to panel
            repaint();
        }
    }

    public void playSound(String fileName) {
        try {
            //Open an audio input stream
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(this.getClass().getResource(fileName));
            //get a sound clip object
            Clip clip = AudioSystem.getClip();
            //play sound
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
