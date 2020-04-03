/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breakergit;

import java.awt.Graphics;

/**
 *
 * @author ricar
 */
public class Ball extends Item {
    private Game game;
    public int colTimer;           // to store a timer for collisions
    public int dirX;
    public int dirY;
    public boolean flagBomb;
    public boolean flagFist;

    public Ball(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
        dirX = 1;
        dirY = -1;
    }

    @Override
    public void tick() {
        setX(getX() + (dirX * 6));
        setY(getY() + (dirY * 6));
        
        if (getX() <= 0 || getX() + 25 > game.getWidth()) {
            dirX *= -1;
        }
        
        if (getY() <= 0 || getY() > game.getHeight()) {
            dirY *= -1;
        }
        colTimer += 1;
    }

    @Override
    public void render(Graphics g) {
        if (flagBomb) {
            g.drawImage(Assets.ballBomb, getX(), getY(), getWidth(), getHeight(), null);
        }
        else if (flagFist) {
            g.drawImage(Assets.ballFist, getX(), getY(), getWidth(), getHeight(), null);
        }
        else {
            g.drawImage(Assets.ball, getX(), getY(), getWidth(), getHeight(), null);
        }
    }
    
    public void setBombFlag(boolean flag) {
        this.flagBomb = flag;
    }
    
    public void setFistFlag(boolean flag) {
        this.flagFist = flag;
    }
    
    public void setColTimer(int time) {
        this.colTimer = time;
    }
    
    public void setDirX(int dirX) {
        this.dirX = dirX;
    }
    
    public void setDirY(int dirY) {
        this.dirY = dirY;
    }
    
    public int getDirX() {
        return dirX;
    }
    
    public int getDirY() {
        return dirY;
    }
}
