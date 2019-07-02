package rummikub;

import rummikub.interfaces.CollisionChecker;
import rummikub.interfaces.GameObject;
import rummikub.interfaces.MoveToFront;
import rummikub.interfaces.WindowLocation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

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
    public JLabel onCreate(Grid grid, WindowLocation loc, MoveToFront mov, CollisionChecker col) {
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

        setupMouseEvents(grid, loc, mov, col);

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

    private void setupMouseEvents(Grid grid, WindowLocation loc, MoveToFront mov, CollisionChecker col){
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

                if(col.checkCollision(Pedra.this, 0) != null){ //se colidiu faz rollback
                    moveTo(grid, locationBeforeDrag.x, locationBeforeDrag.y);
                    locationBeforeDrag = null;
                }
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
