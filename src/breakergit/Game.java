package breakergit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 *
 * @author hgm
 */
public class Game implements Runnable {

    private BufferStrategy bs;                  // to have several buffers when displaying
    private Graphics g;                         // to paint objects
    private Display display;                    // to display in the game
    private String title;                       // title of the window
    private int width;                          // width of the window
    private int height;                         // height of the window
    private int score;                          // to save the player score
    private int lives;                          // to save the player lives
    private boolean running;                    // to set the game
    private Player player;                      // to use the player

    private Thread thread;                      // thread to create the game
    private KeyManager keyManager;              // to manage the keyboard
    private ReadandWrite RW;                    // to manage save and load

    /**
     * to create title, width and height and set the game is still not running
     *
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height to set the height of the window
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;

        lives = 5;
        score = 0;

        keyManager = new KeyManager();
        RW = new ReadandWrite(this);
    }

    /**
     * initializing the display window of the game
     */
    private void init() {
        display = new Display(title, getWidth(), getHeight());
        Assets.init();
        player = new Player(100, 100, 100, 100, this);
        display.getJframe().addKeyListener(keyManager);

        // plays the backSound
        Assets.backSound.setLooping(true);
        Assets.backSound.play();
    }

    /**
     * setting the thread for the game
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * stopping the thread
     */
    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        init();
        // frames per second
        int fps = 60;
        // time for each tick in nano segs
        double timeTick = 1000000000 / fps;
        // initializing delta
        double delta = 0;
        // define now to use inside the loop
        long now;
        // initializing last time to the computer time in nanosecs
        long lastTime = System.nanoTime();
        while (running) {
            // setting the time now to the actual time
            now = System.nanoTime();
            // acumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            // updating the last time
            lastTime = now;

            // if delta is positive we tick the game
            if (delta >= 1) {
                keyManager.tick();
                if (lives > 0 && keyManager.pause == false) {
                    tick();
                }
                render();
                delta--;
            }
        }
        stop();
    }

    private void tick() {
        // ticks
        player.tick();
        RW.tick();
    }

    private void render() {
        // get the buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        /* if it is null, we define one with 3 buffers to display images of
        the game, if not null, then we display every image of the game but
        after clearing the Rectanlge, getting the graphic object from the 
        buffer strategy element. 
        show the graphic and dispose it to the trash system
         */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            g = bs.getDrawGraphics();

            if (lives > 0) {
                if (keyManager.pause) {
                    g.drawImage(Assets.background, 0, 0, width, height, null);

                    // Displays the score with a specific format
                    g.setFont(new Font("Cooper Black", Font.BOLD, 20));
                    g.setColor(Color.red);
                    g.drawString("Score: " + score, getWidth() - 150, 50);

                    // Displays the lives with a specific format
                    g.setFont(new Font("Cooper Black", Font.BOLD, 20));
                    g.setColor(Color.red);
                    g.drawString("Lives: " + lives, getWidth() - 150, 80);

                    // Display Pause signal
                    g.setFont(new Font("Cooper Black", Font.BOLD, 20));
                    g.setColor(Color.red);
                    g.drawString("Pause", getWidth() - 150, 110);

                    player.render(g);
                } else {
                    g.drawImage(Assets.background, 0, 0, width, height, null);

                    // Displays the score with a specific format
                    g.setFont(new Font("Cooper Black", Font.BOLD, 20));
                    g.setColor(Color.red);
                    g.drawString("Score: " + score, getWidth() - 150, 50);

                    // Displays the lives with a specific format
                    g.setFont(new Font("Cooper Black", Font.BOLD, 20));
                    g.setColor(Color.red);
                    g.drawString("Lives: " + lives, getWidth() - 150, 80);

                    player.render(g);
                }
            } else {
                g.drawImage(Assets.gameover, 0, 0, width, height, null);
            }

            bs.show();
            g.dispose();
        }
    }

    //setters
    public void setLives(int life) {
        this.lives = life;
    }

    public void setScore(int x) {
        this.score = x;
    }

    //getters
    /**
     * To get the width of the game window
     *
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the game window
     *
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }

}
