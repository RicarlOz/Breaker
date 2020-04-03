
package breakergit;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author RicardoGomez and HeribertoGil
 */
public class KeyManager implements KeyListener {
    
    public boolean up;      // flag to move up the player
    public boolean down;    // flag to move down the player
    public boolean left;    // flag to move left the player
    public boolean right;   // flag to move right the player
    public boolean space;   //custom flag
    
    public boolean save; // save
    public boolean load; // load
    public boolean pause; // pause
    

    private boolean keys[];  // to store all the flags for every key
    
    public KeyManager() {
        keys = new boolean[256];
        pause = false;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // set true to every key pressed except key S and L
        if(e.getKeyCode() != KeyEvent.VK_S && e.getKeyCode() != KeyEvent.VK_L){
            keys[e.getKeyCode()] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // set false to every key released and true to key S, L and P
        switch(e.getKeyCode()){
            case KeyEvent.VK_S:
                keys[e.getKeyCode()] = true;
                break;
            case KeyEvent.VK_L:
                keys[e.getKeyCode()] = true;
                break;
            case KeyEvent.VK_P:
                pause = !pause;
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
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
        save = keys[KeyEvent.VK_S];
        load = keys[KeyEvent.VK_L];
        
        keys[KeyEvent.VK_S] = false;
        keys[KeyEvent.VK_L] = false;
    }
}
