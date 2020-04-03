package breakergit;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;

/**
 *
 * @author hgm
 */
public class ReadandWrite {
    
    Game game;
    int vidas;
    int score;
    
    ReadandWrite(Game game){
        this.game = game;
    }
    
    public void Save(String strFileName){
        try{
            System.out.println("Saving...");
            PrintWriter writer = new PrintWriter(new FileWriter(strFileName));
            vidas = game.getLives();
            score = game.getScore();
            writer.println("" + vidas + "/" + score);
            writer.close();
        }
        catch(IOException ioe){
            System.out.println("File Not fund Call 911");
        }
    }
    
    public void Load(String strFileName){
        try{
            System.out.println("Loading...");
            FileReader file = new FileReader(strFileName);
            BufferedReader reader = new BufferedReader(file);
            String line;
            String datos[];
            line = reader.readLine();
            datos = line.split("/");
            game.setLives(Integer.parseInt(datos[0]));
            game.setScore(Integer.parseInt(datos[1]));
            System.out.println("Se leyo vidas = " + vidas + " y score = " + score);
            reader.close();
        }
        catch(IOException e){
            System.out.println("File Not fund Call 911");
        }
    }
    
    public void tick() {
        if (game.getKeyManager().save) {
            Save("saves.txt");
        }
        if (game.getKeyManager().load) {
            Load("saves.txt");
        }
    }
}
