package breakergit;

import java.awt.image.BufferedImage;

/**
 *
 * @author RicardoGomez and HeribertoGil
 */
public class Assets {

    public static BufferedImage background; // to store background image
    public static BufferedImage gameover;   // to store the gameover screen
    public static SoundClip backSound;      // to store the backSound
    public static SoundClip point;          // to store the points sound

    public static BufferedImage playerSprites;      // to store the player sprites
    public static BufferedImage ringSprites;       // to store the ring sprites
    public static BufferedImage playerFrames[];      // pictures to animate player
    public static BufferedImage ring[];            // pictures spin ring

    /**
     * initializing the images of the game
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/Background.png"/*imagen de fondo*/);
        gameover = ImageLoader.loadImage("/images/gameover.png"/*imagen gameover*/);
        backSound = new SoundClip("/sounds/backSound.wav"/*Sonido background*/);
        point = new SoundClip("/sounds/crash.wav"/*Sonido*/);

        // getting the sprites frome the picture
        playerSprites = ImageLoader.loadImage("/images/pokeballsprites.png"/*Imagen de sprites*/);
        ringSprites = ImageLoader.loadImage("/images/ring2sprites.png"/*Imagen de sprites*/);
        
        // creating array of images before animations
        SpriteSheet playerspritesheet = new SpriteSheet(playerSprites);     //crea objeto para cortar los sprites en frames
        SpriteSheet ringspritesheet = new SpriteSheet(ringSprites);     //crea objeto para cortar los sprites en frames
        playerFrames = new BufferedImage[12];   //crea array para guardar los frames de la animacion
        ring = new BufferedImage[8];        //crea array para guardar los frames de la animacion
        
        // croping the pictures from the sheet into the array
        for (int i = 11; i >= 0; i--) {
            playerFrames[11-i] = playerspritesheet.crop(i*64, 0, 64, 64);     //ciclo para guardar los frames
        }
        for (int i = 0; i < 8; i++) {
            ring[i] = ringspritesheet.crop(i*350, 0, 350, 306);         //ciclo para guardar los frames
        }
        
    }

}
