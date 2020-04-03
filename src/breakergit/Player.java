package breakergit;

import java.awt.Graphics;

/**
 *
 * @author hgm
 */
public class Player extends Item {

    private Game game;

    public Player(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
    }

    @Override
    public void tick() {
        if (game.getKeyManager().left) {
            setX(getX() - 1);
        }
        if (game.getKeyManager().right) {
            setX(getX() + 1);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, getX(), getY(), getWidth(), getHeight(), null);
    }

}
