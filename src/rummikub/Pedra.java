package rummikub;

import rummikub.interfaces.GameObject;

import javax.swing.*;

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

    public Pedra(String tipo, String cor){
        this.tipo = tipo;
        this.cor = cor;
    }
    public Pedra(String tipo){
        this.tipo = tipo;
    }

    @Override
    public JLabel onCreate(Grid grid) {
        versoSprite = new ImageIcon(Utils.getResource("assets/pedra_verso.png"));

        if(tipo.equals(TIPO_CORINGA)) {
            frenteSprite = new ImageIcon(Utils.getResource("assets/pedra_" + tipo + (coringaFlip ? "_2" : "") + ".png"));
            coringaFlip = !coringaFlip;
        }
        else
            frenteSprite = new ImageIcon(Utils.getResource("assets/" + cor + "/" + tipo + ".png"));

        spriteHolder =  new JLabel(frenteSprite);
        spriteHolder.setLocation(0, 0);
        spriteHolder.setBounds(0, 0, versoSprite.getIconWidth(), versoSprite.getIconHeight());

        return spriteHolder;
    }

    @Override
    public void onUpdate(Grid grid) {

    }

    @Override
    public void onDestroy(Grid grid) {

    }

    /**
     * Move a pedra de sua posição atual para as coordenadas de destino
     * @param grid instância da classe Grid
     * @param x coordenada X de destino
     * @param y coordenada Y de destino
     */
    public void moveTo(Grid grid, int x, int y){
        spriteHolder.setLocation(grid.snapCoordinateToGrid(x), grid.snapCoordinateToGrid(y));
    }
}
