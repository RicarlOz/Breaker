package breakergit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

/**
 *
 * @author RicardoGomez and HeribertoGil
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
    private int timerBomb;                      // to store a timer for bomb powerUp
    private int timerFist;                      // to store a timer for fist powerUp
    private int tickNo;                         // to store the tick number
    private boolean running;                    // to set the game
    private Player player;                      // to use the player
    private LinkedList<Live> hearts;            // to store the hearts
    private LinkedList<Ball> balls;             // to store the ball
    private LinkedList<PowerUp> powerUps;       // to use plus powerUp
    private BlockSet conjunto;

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
        conjunto = new BlockSet(this);
    }

    /**
     * initializing the display window of the game and items
     */
    private void init() {
        display = new Display(title, getWidth(), getHeight());
        Assets.init();
        display.getJframe().addKeyListener(keyManager);
        conjunto.init();
        
        player = new Player(getWidth() / 2 - 32, getHeight() - 70, 100, 40, this);
        balls = new LinkedList();
        Ball ball = new Ball(getWidth() / 2 - 64, getHeight() - 90, 25, 25, this);
        balls.add(ball);
        powerUps = new LinkedList();
        PowerUp powerUp = new PowerUp(1, getWidth() / 2, 15, 25, 25, this);
        powerUps.add(powerUp);
        hearts = new LinkedList();
        for (int i = 1; i <= lives; i++) {
            Live live = new Live(getWidth() - (35 * i), 10, 25, 25, this);
            hearts.add(live);
        }

        // plays the backSound
        Assets.backSound.setLooping(true);
        //Assets.backSound.play();
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

    /**
     * ticks of the game
     */
    private void tick() {
        // ticks
        player.tick();
        for (int i = 0; i < balls.size(); i++) {
            balls.get(i).tick();

            if (player.collisionX(balls.get(i)) && balls.get(i).colTimer > 30) {
                balls.get(i).setDirY(balls.get(i).getDirY() * -1);
                balls.get(i).setColTimer(0);
            }

            if (player.collisionY(balls.get(i)) && balls.get(i).colTimer > 30) {
                balls.get(i).setDirX(balls.get(i).getDirY() * -1);
                balls.get(i).setColTimer(0);
            }

            if (balls.get(i).getY() >= getHeight()) {
                //lives--;
                //hearts.removeLast();
            }
        }
        for (int i = 0; i < powerUps.size(); i++) {
            powerUps.get(i).tick();

            if (player.collisionX(powerUps.get(i))) {
                switch (powerUps.get(i).getID()) {
                    case 1:
                        Ball ball = new Ball(balls.get(0).getX(), balls.get(0).getY(), 25, 25, this);
                        balls.add(ball);
                        break;
                    case 2:
                        for (int j = 0; j < balls.size(); j++) {
                            if (balls.get(i).flagFist) {
                                balls.get(j).setFistFlag(false);
                            }
                            balls.get(j).setBombFlag(true);
                            timerBomb = 0;
                        }
                        break;
                    case 3:
                        for (int j = 0; j < balls.size(); j++) {
                            if (balls.get(i).flagFist) {
                                balls.get(j).setFistFlag(false);
                            }
                            balls.get(j).setFistFlag(true);
                            timerFist = 0;
                        }
                        break;
                    default:
                        break;
                }
                powerUps.remove(i);
            }
        }
        for (int i = 0; i < hearts.size(); i++) {
            hearts.get(i).tick();
        }
        tickNo += 1;
        if (tickNo % 60 == 0) {
            timerBomb += 1;
            timerFist += 1;
        }

        if (timerBomb == 10) {
            for (int i = 0; i < balls.size(); i++) {
                balls.get(i).setBombFlag(false);
            }
        }

        if (timerFist == 10) {
            for (int i = 0; i < balls.size(); i++) {
                balls.get(i).setFistFlag(false);
            }
        }
        
        conjunto.tick();
        RW.tick();
    }

    /**
     * rendering each element of the game
     */
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
                    g.setFont(new Font("Impact", Font.BOLD, 20));
                    g.setColor(Color.red);
                    g.drawString("Score: " + score, 15, 25);

                    // Displays the lives with a specific format
                    //g.setFont(new Font("Cooper Black", Font.BOLD, 20));
                    //g.setColor(Color.red);
                    //g.drawString("Lives: " + lives, getWidth() - 150, 80);
                    // Display Pause signal
                    g.setFont(new Font("Cooper Black", Font.BOLD, 20));
                    g.setColor(Color.red);
                    g.drawString("Pause", getWidth() - 150, 110);

                    for (int i = 0; i < balls.size(); i++) {
                        balls.get(i).render(g);
                    }

                    for (int i = 0; i < powerUps.size(); i++) {
                        powerUps.get(i).render(g);
                    }

                    for (int i = 0; i < hearts.size(); i++) {
                        hearts.get(i).render(g);
                    }
                    player.render(g);
                    conjunto.render();
                } else {
                    g.drawImage(Assets.background, 0, 0, width, height, null);

                    // Displays the score with a specific format
                    g.setFont(new Font("Impact", Font.BOLD, 20));
                    g.setColor(Color.red);
                    g.drawString("Score: " + score, 15, 25);

                    // Displays the lives with a specific format
                    //g.setFont(new Font("Cooper Black", Font.BOLD, 20));
                    //g.setColor(Color.red);
                    //g.drawString("Lives: " + lives, getWidth() - 150, 80);
                    for (int i = 0; i < balls.size(); i++) {
                        balls.get(i).render(g);
                    }

                    for (int i = 0; i < powerUps.size(); i++) {
                        powerUps.get(i).render(g);
                    }

                    for (int i = 0; i < hearts.size(); i++) {
                        hearts.get(i).render(g);
                    }
                    player.render(g);
                    conjunto.render();
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

    /**
     * To get the lives
     *
     * @return an <code>int</code> value with the lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * To get the score
     *
     * @return an <code>int</code> value with the score
     */
    public int getScore() {
        return score;
    }
    
    public ReadandWrite getRW(){
        return RW;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }
    
    public LinkedList<Ball> getBalls(){
        return balls;
    }
    
    public Graphics getGraphics(){
        return g;
    }
}
