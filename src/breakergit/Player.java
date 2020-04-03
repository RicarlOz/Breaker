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
            setX(getX() - 7);
        }
        if (game.getKeyManager().right) {
            setX(getX() + 7);
        }

        // reset x position if gets out of the screen
        if (getX() + width >= game.getWidth()) {
            setX(game.getWidth() - width);
        } else if (getX() <= 0) {
            setX(0);
        }
        
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, getX(), getY(), getWidth(), getHeight(), null);
    }

}
