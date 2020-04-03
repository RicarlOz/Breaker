package breakergit;

import java.awt.Point;
import java.util.LinkedList;

/**
 *
 * @author RicardoGomez and HeribertoGil
 */
public class BlockSet {

    Game game;
    ReadandWrite RW;
    LinkedList<Block> bloques;
    LinkedList countToques;

    int nivel;
    String strLevel;

    public BlockSet(Game game) {
        this.game = game;
        this.RW = game.getRW();
        bloques = new LinkedList();
        countToques = new LinkedList();
    }

    public void init() {
        nivel = 1;
        strLevel = "Level" + nivel + ".txt";
        LinkedList<Point> xy = RW.LoadNivel(strLevel);
        generaBloques(xy);
    }

    //funcion de aumento de nivel
    public void aumentarNivel() {
        nivel++;
        strLevel = "Level" + nivel + ".txt";
        LinkedList<Point> xy = RW.LoadNivel(strLevel);
        generaBloques(xy);
    }

    //construccion de bloques
    public void generaBloques(LinkedList<Point> p) {
        //System.out.println("generaBloques()");
        Block temp;
        for (Point punto : p) {
            //generar bloque
            temp = new Block(punto.x, punto.y, 120, 40, this);
            bloques.add(temp);
            //System.out.println("size de Bloques: " + bloques.size());
        }
    }

    //tickeo de conjunto que hace llamada a todos los tick de bloques
    public void tick() {
        //no llama a tick porque los bloques no se mueven
        if (!bloques.isEmpty()) {
            for (Ball bola : game.getBalls()) {
                for (int i = 0; i < bloques.size(); i++) {
                    //checar colisiones con la bola
                    if (bloques.get(i).collisionY(bola)) {
                        System.out.println("toque con left/right");

                        //instruccion para rebotar la pelotacuando choca por el lado
                        bola.setDirX(bola.getDirX() * -1);

                        //si al chocar el bloque ya esta crackeado se borra el objeto, si no, se crackea
                        if (bloques.get(i).isCracked()) {
                            System.out.println("Segundo toque");
                            bloques.remove(i);
                        } else {
                            System.out.println("Primer toque");
                            bloques.get(i).touch();
                        }
                    }
                    if (bloques.get(i).collisionX(bola)) {
                        System.out.println("toque con top/bot");

                        //instruccion para rebotar la pelotacuando choca por arriba o abajo
                        bola.setDirY(bola.getDirY() * -1);

                        //si al chocar el bloque ya esta crackeado se borra el objeto, si no, se crackea
                        if (bloques.get(i).isCracked()) {
                            System.out.println("Segundo toque");
                            bloques.remove(i);
                        } else {
                            System.out.println("Primer toque");
                            bloques.get(i).touch();
                        }
                    }
                }
            }
        } else {
            //revisa si acabo el juego, si no ha acabado aumenta nivel
            if (nivel == 3) {
                init(); // temporal, hay que definir pantalla de juego ganado
            } else {
                aumentarNivel();
            }
        }
    }

    //render que hace llamada a todos los render de bloques
    public void render() {
        for (Block bloque : bloques) {
            bloque.render(game.getGraphics());
        }
    }

}
