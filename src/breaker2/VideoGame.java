package breaker2;

/**
 *
 * @author RicardoGomez and HeribertoGil
 */
public class VideoGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Game g = new Game("Juego", 1024, 600);
        g.start();
    }
    
}
