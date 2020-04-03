package breakergit;

import java.awt.Graphics;

/**
 *
 * @author RicardoGomez and HeribertoGil
 */
public class Block extends Item {

    private BlockSet conjunto;
    private boolean Cracked;

    public Block(int x, int y, int width, int height, BlockSet conjunto) {
        super(x, y, width, height);
        this.conjunto = conjunto;
        this.Cracked = false;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        if (Cracked) {
            switch (conjunto.nivel) {
                case 1:
                    g.drawImage(Assets.block1b, x, y, width, height, null);
                    break;
                case 2:
                    g.drawImage(Assets.block2b, x, y, width, height, null);
                    break;
                case 3:
                    g.drawImage(Assets.block3b, x, y, width, height, null);
                    break;
            }
        } else {
            switch (conjunto.nivel) {
                case 1:
                    g.drawImage(Assets.block1a, x, y, width, height, null);
                    break;
                case 2:
                    g.drawImage(Assets.block2a, x, y, width, height, null);
                    break;
                case 3:
                    g.drawImage(Assets.block3a, x, y, width, height, null);
                    break;
            }
        }
    }

    public boolean isCracked() {
        return Cracked;
    }

    public void touch() {
        Cracked = true;
    }

}
