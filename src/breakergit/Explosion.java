package breakergit;

import java.awt.Graphics;

/**
 *
 * @author RicardoGomez and HeribertoGil
 */
public class Explosion extends Item {
    
    private Game game;
    private Animation animation;
    private int tickNo;                 // to store the tick number
    private int timerExp;               // to store a timer for explosions

    public Explosion(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
        this.animation = new Animation(Assets.explosionFrames, 150);
        tickNo = 0;
        timerExp = 0;
    }

    @Override
    public void tick() {
        this.animation.tick();
        tickNo += 1;
        if (tickNo % 60 == 0) {
            timerExp++;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(animation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }
    
    public int getTimer() {
        return timerExp;
    }
    
    public int getTickNo() {
        return tickNo;
    }
}
