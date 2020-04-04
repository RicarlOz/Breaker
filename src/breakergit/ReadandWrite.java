package breakergit;

import java.awt.Point;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.util.LinkedList;

/**
 *
 * @author RicardoGomez and HeribertoGil
 */
public class ReadandWrite {

    Game game;
    int vidas;
    int score;
    //LinkedList<Point> puntos;

    ReadandWrite(Game game) {
        this.game = game;
        //puntos = new LinkedList();
    }

    public void Save(String strFileName) {
        try {
            System.out.println("Saving...");
            PrintWriter writer = new PrintWriter(new FileWriter(strFileName));
            vidas = game.getLives();
            score = game.getScore();
            writer.println("" + vidas + "/" + score);
            writer.close();
        } catch (IOException ioe) {
            System.out.println("File Not fund Call 911");
        }
    }

    public void Load(String strFileName) {
        try {
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
        } catch (IOException e) {
            System.out.println("File Not fund Call 911");
        }
    }

    //debe retornar una LinkedList de points que indiquen la poscicion de los bloques
    public LinkedList<Point> LoadNivel(String strFileName) {
        Point temp;
        LinkedList<Point> puntos;
        puntos = new LinkedList();

        try {
            System.out.println(strFileName);
            System.out.println("Loading Next Level...");
            FileReader file = new FileReader(strFileName);
            BufferedReader reader = new BufferedReader(file);
            String line;
            String datos[];
            //ciclo para leer archivo por lineas / transformar datos en puntos y agregarlos a un array
            while (true) {
                line = reader.readLine();
                if (line.equals("eof")) {
                    break;
                }
                datos = line.split("/");
                temp = new Point(Integer.parseInt(datos[0]), Integer.parseInt(datos[1]));

                puntos.add(temp);

                //System.out.println("size de puntos: "+puntos.size());
                //System.out.println("x: " + temp.x + " y: " + temp.y);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("File Not fund Call 911");
        }
        return puntos;
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
