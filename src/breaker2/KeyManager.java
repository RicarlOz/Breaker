
package breaker2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author hgm
 */
public class KeyManager implements KeyListener {
    
    public boolean up;      // flag to move up the player
    public boolean down;    // flag to move down the player
    public boolean left;    // flag to move left the player
    public boolean right;   // flag to move right the player
    public boolean space;   //custom flag
    
    public boolean s; // save
    public boolean l; // load
    public boolean p; // pause
    

    private boolean keys[];  // to store all the flags for every key
    
    public KeyManager() {
        keys = new boolean[256];
        p = false;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // set true to every key released
        //keys[e.getKeyCode()] = true;
        
        if(e.getKeyCode() != KeyEvent.VK_S && e.getKeyCode() != KeyEvent.VK_L){
            keys[e.getKeyCode()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // set false to every key released
        //keys[e.getKeyCode()] = false;
        
        switch(e.getKeyCode()){
            case KeyEvent.VK_S:
                keys[e.getKeyCode()] = true;
                break;
            case KeyEvent.VK_L:
                keys[e.getKeyCode()] = true;
                break;
            case KeyEvent.VK_P:
                p = !p;
                break;
            default:
                keys[e.getKeyCode()] = false;
                break;
        }
    }
    
    /**
     * to enable or disable moves on every tick
     */
    public void tick() {
        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
        space = keys[KeyEvent.VK_SPACE];
        
        s = keys[KeyEvent.VK_S];
        l = keys[KeyEvent.VK_L];
        
        keys[KeyEvent.VK_S] = false;
        keys[KeyEvent.VK_L] = false;
    }
}
