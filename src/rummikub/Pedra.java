package rummikub;

import rummikub.interfaces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class Pedra implements GameObject {
    public static final String TIPO_NUMERO_1 = "1";
    public static final String TIPO_NUMERO_2 = "2";
    public static final String TIPO_NUMERO_3 = "3";
    public static final String TIPO_NUMERO_4 = "4";
    public static final String TIPO_NUMERO_5 = "5";
    public static final String TIPO_NUMERO_6 = "6";
    public static final String TIPO_NUMERO_7 = "7";
    public static final String TIPO_NUMERO_8 = "8";
    public static final String TIPO_NUMERO_9 = "9";
    public static final String TIPO_NUMERO_10 = "10";
    public static final String TIPO_NUMERO_11 = "11";
    public static final String TIPO_NUMERO_12 = "12";
    public static final String TIPO_NUMERO_13 = "13";
    public static final String TIPO_CORINGA = "joker";

    public static final String COR_AZUL = "azul";
    public static final String COR_VERDE = "verde";
    public static final String COR_VERMELHO = "vermelho";
    public static final String COR_ROSA = "rosa";



    //essa variável serve para alternar entre as duas variedades de imagens para o coringa
    private static boolean coringaFlip = false;

    private JLabel spriteHolder;
    private ImageIcon versoSprite;
    private ImageIcon frenteSprite;

    private String tipo;
    private String cor;

    private Point locationBeforeDrag;

    private Conjunto conjunto;

    public Pedra(String tipo, String cor){
        this.tipo = tipo;
        this.cor = cor;
    }
    public Pedra(String tipo){
        this.tipo = tipo;
    }

    @Override
    public JLabel onCreate(Grid grid, WindowLocation loc, MoveToFront mov, CollisionChecker col, GerenciadorDeConjuntos conj) {
        versoSprite = new ImageIcon(Utils.getResource("assets/pedra_verso.png"));

        if(tipo.equals(TIPO_CORINGA)) {
            frenteSprite = new ImageIcon(Utils.getResource("assets/pedra_" + tipo + (coringaFlip ? "_2" : "") + ".png"));
            coringaFlip = !coringaFlip;
        }
        else
            frenteSprite = new ImageIcon(Utils.getResource("assets/" + cor + "/" + tipo + ".png"));

        spriteHolder =  new JLabel(versoSprite);
        spriteHolder.setLocation(0, 0);
        spriteHolder.setBounds(0, 0, versoSprite.getIconWidth(), versoSprite.getIconHeight());

        setupMouseEvents(grid, loc, mov, col, conj);

        return spriteHolder;
    }

    @Override
    public void onUpdate(Grid grid, WindowLocation loc, MoveToFront mov, CollisionChecker col) {

    }

    @Override
    public void onDestroy(Grid grid, WindowLocation loc, MoveToFront mov, CollisionChecker col) {

    }

    /**
     * Move a pedra de sua posição atual para as coordenadas de destino
     * @param grid instância da classe Grid
     * @param x coordenada X de destino
     * @param y coordenada Y de destino
     */
    public void moveTo(Grid grid, int x, int y){
        moveToNoSnapping(grid.snapCoordinateToGrid(x), grid.snapCoordinateToGrid(y));
    }

    /**
     * Move a pedra de sua posição atual para as coordenadas de destino sem respeitar o grid
     * @param x coordenada X de destino
     * @param y coordenada Y de destino
     */
    private void moveToNoSnapping(int x, int y){
        spriteHolder.setLocation(x, y);
    }

    public String getTipo() {
        return tipo;
    }

    public String getCor() {
        return cor;
    }

    public Point getLocation(){
        return spriteHolder.getLocation();
    }

    public void desvirar(){
        this.spriteHolder.setIcon(frenteSprite);
    }

    public void setConjunto(Conjunto conjunto){
        this.conjunto = conjunto;
    }

    private void setupMouseEvents(Grid grid, WindowLocation loc, MoveToFront mov, CollisionChecker col, GerenciadorDeConjuntos conj){
        spriteHolder.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                mov.moveToFront(spriteHolder);

                locationBeforeDrag = spriteHolder.getLocation();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mousePressed(e);

                final Point position = spriteHolder.getLocation();
                moveTo(grid, position.x, position.y);

                // simplesmente para fins de debug, printa o estado atual dos conjuntos lógicos pro stdout
                final Runnable log = () -> {
                    System.out.println();
                    for(Conjunto c : conj.getConjuntos())
                        System.out.println(c + " -> seq? " + (c.isSequencia() ? "s" : "n") + "; grupo? " + (c.isGrupo() ? "s" : "n") + " -> " + c.getPontos() + "pts");
                };

                final Conjunto oldConjunto = Pedra.this.conjunto;

                // se remove do conjunto antigo
                final ArrayList<Conjunto> split = oldConjunto == null ? null : oldConjunto.split(Pedra.this);
                if(oldConjunto != null) {
                    conj.removeConjunto(oldConjunto);

                    for (Conjunto c : split)
                        if (c.size() > 0)
                            conj.addConjunto(c);
                }

                final Runnable rollback = () -> {
                    moveTo(grid, locationBeforeDrag.x, locationBeforeDrag.y);
                    locationBeforeDrag = null;

                    if(oldConjunto != null) {
                        // desfaz o split
                        for (Conjunto c : split)
                            conj.removeConjunto(c);
                        final Conjunto c = new Conjunto(oldConjunto.getPedras());
                        conj.addConjunto(c);
                    }

                    log.run();
                };

                if(col.checkCollision(Pedra.this, 0) != null){ //se colidiu faz rollback
                    rollback.run();
                    return;
                }

                final Pedra vizinhoEsquerda = col.checkCollision(Pedra.this, -grid.getCellSizeInPx());
                final Pedra vizinhoDireita = col.checkCollision(Pedra.this, grid.getCellSizeInPx());

                if(vizinhoEsquerda != null && vizinhoDireita != null) { //não pode mover, pois o uniria dois conjuntos distintos
                    rollback.run();
                    return;
                }

                final Pedra vizinho = vizinhoEsquerda != null ? vizinhoEsquerda : vizinhoDireita;

                if(vizinho != null){
                    vizinho.conjunto.add(Pedra.this);
                    vizinho.conjunto.sort(grid);
                }
                else {
                    final Conjunto c = new Conjunto(new ArrayList<>());
                    c.add(Pedra.this);
                    conj.addConjunto(c);
                }

                log.run();
            }
        });

        spriteHolder.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);

                final Point absoluteMouse = MouseInfo.getPointerInfo().getLocation();
                final Point windowLocation = loc.getLocation();
                final Point mouse = Utils.screenCoordinatesToWindowCoordinates(windowLocation, absoluteMouse);

                moveToNoSnapping(mouse.x - grid.getCellSizeInPx() / 2, mouse.y - grid.getCellSizeInPx() / 2);
            }
        });
    }
}
