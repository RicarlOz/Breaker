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
        Block temp;
        for (int i = 0; i < p.size(); i++) {
            //System.out.println("punto = ("+p.get(i).x+","+p.get(i).y+")");
            //generar bloque
            temp = new Block(p.get(i).x, p.get(i).y, 90, 30, this);
            bloques.add(temp);
            //System.out.println("size de Bloques: " + bloques.size());
        }
    }

    public void restauraBloques(LinkedList<Point> p) {

        bloques.clear();

        Block temp;
        for (int i = 0; i < p.size(); i++) {
            //System.out.println("punto = ("+p.get(i).x+","+p.get(i).y+")");
            //generar bloque
            temp = new Block(p.get(i).x, p.get(i).y, 90, 30, this);
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
                    if (bloques.get(i).collisionY(bola) || bloques.get(i).collisionX(bola)) {
                        if (bloques.get(i).collisionY(bola)) {
                            //instruccion para rebotar la pelota cuando choca por el lado
                            bola.setDirX(bola.getDirX() * -1);
                        }

                        if (bloques.get(i).collisionX(bola)) {
                            //instruccion para rebotar la pelota cuando choca por arriba o abajo
                            bola.setDirY(bola.getDirY() * -1);
                        }
                        
                        if (bola.flagFist) {
                            Assets.destroy.play();
                            game.setScore(game.getScore() + 15);
                            bloques.remove(i);
                        }
                        else if (bola.flagBomb) {
                            game.explosion(bola.getX(), bola.getY());
                            for(int j = 0; j < bloques.size(); j++) {
                                for (int k = 0; k < game.getExpSize(); k++) {
                                    if (bloques.get(j).collisionExp(game.getExp(k)) && game.getExp(k).getTickNo() < 2) {
                                        if (bloques.get(i).isCracked()) {
                                            game.powerUp(bloques.get(i).getX(), bloques.get(i).getY());
                                            Assets.destroy.play();
                                            game.setScore(game.getScore() + 15);
                                            bloques.remove(i);
                                            break;
                                        } else {
                                            bloques.get(i).touch();
                                            Assets.crash.play();
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            //si al chocar el bloque ya esta crackeado se borra el objeto, si no, se crackea
                            if (bloques.get(i).isCracked()) {
                                game.powerUp(bloques.get(i).getX(), bloques.get(i).getY());
                                Assets.destroy.play();
                                game.setScore(game.getScore() + 15);
                                bloques.remove(i);
                            } else {
                                bloques.get(i).touch();
                                Assets.crash.play();
                            }
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
