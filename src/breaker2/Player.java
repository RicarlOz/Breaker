
package breaker2;

import java.awt.Graphics;

/**
 *
 * @author hgm
 */
public class Player extends Item {
    private Game game;
    private Animation animation;
    
    private int dx;
    private int dy;
    
    public Player(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
        this.animation = new Animation(Assets.PlayerSpin, 150);
    }
    
    

    @Override
    public void tick() {
        
        //movimiento con mouse (en cualquier parte de la hitbox)
        if(game.getMouseManager().pressed && hold(game.getMouseManager().getPunto())){
            if(game.getMouseManager().dxdyHelper){
                dx = game.getMouseManager().x - getX();
                dy = game.getMouseManager().y - getY();
            }
            //System.out.println(""+dx+"/"+dy);
            setX(game.getMouseManager().x - dx);
            setY(game.getMouseManager().y - dy);
        }

        //movimiento con keyManager
        if(game.getKeyManager().up){
            setY(y-5);
        }
        if(game.getKeyManager().down){
            setY(y+5);
        }
        if(game.getKeyManager().right){
            setX(x+5);
        }
        if(game.getKeyManager().left){
            setX(x-5);
        }
        
        
        this.animation.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(animation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }
    
}
