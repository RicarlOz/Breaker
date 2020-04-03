package breakergit;

import java.awt.Graphics;

/**
 *
 * @author RicardoGomez and HeribertoGil
 */
public class Live extends Item {

    private Game game;
    private Animation animation;

    public Live(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
        this.animation = new Animation(Assets.liveFrames, 150);
    }

    @Override
    public void tick() {
        this.animation.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(animation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }
}
