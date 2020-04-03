package breakergit;

import java.awt.Graphics;

/**
 *
 * @author RicardoGomez and HeribertoGil
 */
public class PowerUp extends Item {
    private Game game;
    public int id;

    public PowerUp(int id, int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
        this.id = id;
    }

    @Override
    public void tick() {
        setY(getY() + 3);
    }

    @Override
    public void render(Graphics g) {
        switch (getID()) {
            case 1:
                g.drawImage(Assets.plus, getX(), getY(), getWidth(), getHeight(), null);
                break;
            case 2:
                g.drawImage(Assets.bomb, getX(), getY(), getWidth(), getHeight(), null);
                break;
            case 3:
                g.drawImage(Assets.fist, getX(), getY(), getWidth(), getHeight(), null);
                break;
            default:
                break;
        }
    }
    
    public int getID() {
        return id;
    }
}
