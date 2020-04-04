package breakergit;

import java.awt.image.BufferedImage;

/**
 *
 * @author RicardoGomez and HeribertoGil
 */
public class Assets {

    public static BufferedImage background; // to store background image
    public static BufferedImage gameover;   // to store the gameover screen
    public static BufferedImage player;     // to store player image
    public static BufferedImage block1a;    // to store block1a image
    public static BufferedImage block1b;    // to store block1b image
    public static BufferedImage block2a;    // to store block2a image
    public static BufferedImage block2b;    // to store block2b image
    public static BufferedImage block3a;    // to store block3a image
    public static BufferedImage block3b;    // to store block3b image
    public static BufferedImage bomb;       // to store bomb powerUp image
    public static BufferedImage fist;       // to store fist powerUp image
    public static BufferedImage plus;       // to store plus powerUp image
    public static BufferedImage ball;       // to store ball image
    public static BufferedImage ballBomb;   // to store ball image
    public static BufferedImage ballFist;   // to store ball image
    
    public static BufferedImage liveSprites;        // to store the live sprites
    public static BufferedImage liveFrames[];       // pictures to animate live
    public static BufferedImage explosionSprites;   // to store explosion sprites
    public static BufferedImage explosionFrames[];  // pictures to animate explosion
    
    public static SoundClip backSound;      // to store the back sound
    public static SoundClip crash;          // to store the crash sound
    public static SoundClip destroy;        // to store the destroy sound
    public static SoundClip loseLive;       // to store the loseLive sound
    public static SoundClip powerUp;        // to store the powerUp sound
    
    /**
     * initializing the images and sounds of the game
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/background.jpg");
        gameover = ImageLoader.loadImage("/images/gameover.png");
        block1a = ImageLoader.loadImage("/images/block1a.png");
        block1b = ImageLoader.loadImage("/images/block1b.png");
        block2a = ImageLoader.loadImage("/images/block2a.png");
        block2b = ImageLoader.loadImage("/images/block2b.png");
        block3a = ImageLoader.loadImage("/images/block3a.png");
        block3b = ImageLoader.loadImage("/images/block3b.png");
        bomb = ImageLoader.loadImage("/images/bomb.png");
        fist = ImageLoader.loadImage("/images/fist.png");
        plus = ImageLoader.loadImage("/images/plus.png");
        player = ImageLoader.loadImage("/images/player.png");
        ball = ImageLoader.loadImage("/images/ball.png");
        ballBomb = ImageLoader.loadImage("/images/ballBomb.png");
        ballFist = ImageLoader.loadImage("/images/ballFist.png");
        
        backSound = new SoundClip("/sounds/backSound.wav");
        crash = new SoundClip("/sounds/crash.wav");
        destroy = new SoundClip("/sounds/destroy.wav");
        loseLive = new SoundClip("/sounds/loseLive.wav");
        powerUp = new SoundClip("/sounds/powerUp.wav");
        
        
        // getting the sprites frome the picture
        liveSprites = ImageLoader.loadImage("/images/livesSS.png");
        explosionSprites = ImageLoader.loadImage("/images/explosion.png");
        
        // creating array of images before animations
        SpriteSheet liveSS = new SpriteSheet(liveSprites);
        SpriteSheet explosionSS = new SpriteSheet(explosionSprites);
        liveFrames = new BufferedImage[8];
        explosionFrames = new BufferedImage[13];
        
        // croping the pictures from the sheet into the array
        for (int i = 0; i < 8; i++) {
            liveFrames[i] = liveSS.crop(i*493, 0, 493, 443);
        }
        for (int i = 0; i < 13; i++) {
            explosionFrames[i] = explosionSS.crop(i*196, 0, 196, 190);
        }
        
    }

}
