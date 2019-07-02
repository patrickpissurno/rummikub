package rummikub;

import rummikub.interfaces.*;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Botao implements GameObject {

    private JLabel spriteHolder;

    private String tipo;

    private ImageIcon disabledButton;
    private ImageIcon downButton;
    private ImageIcon button;

    private boolean isEnabled;

    public static final String TIPO_PASSAR_A_VEZ = "passar_vez";
    public static final String TIPO_RUMMIKUB = "rummikub";
    public static final String TIPO_FINALIZAR_JOGADA = "finalizar_jogada";

    private Runnable clickListener;

    public Botao(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public JLabel onCreate(Grid grid, WindowLocation loc, MoveToFront mov, CollisionChecker col, GerenciadorDeConjuntos conj, GameUIs ui) {
        if (tipo.equals(TIPO_PASSAR_A_VEZ)) {
            disabledButton = new ImageIcon(Utils.getResource("assets/buttons/btn_passar_a_vez/btn_passar_a_vez_disabled.png"));
            downButton = new ImageIcon(Utils.getResource("assets/buttons/btn_passar_a_vez/btn_passar_a_vez_down.png"));
            button = new ImageIcon(Utils.getResource("assets/buttons/btn_passar_a_vez/btn_passar_a_vez.png"));
        }
        else if (tipo.equals(TIPO_RUMMIKUB)) {
            disabledButton = new ImageIcon(Utils.getResource("assets/buttons/btn_rummikub/btn_rummikub_disabled.png"));
            downButton = new ImageIcon(Utils.getResource("assets/buttons/btn_rummikub/btn_rummikub_down.png"));
            button = new ImageIcon(Utils.getResource("assets/buttons/btn_rummikub/btn_rummikub.png"));
        }
        else if (tipo.equals(TIPO_FINALIZAR_JOGADA)) {
            disabledButton = new ImageIcon(Utils.getResource("assets/buttons/btn_finalizar_jogada/btn_finalizar_jogada_disabled.png"));
            downButton = new ImageIcon(Utils.getResource("assets/buttons/btn_finalizar_jogada/btn_finalizar_jogada_down.png"));
            button = new ImageIcon(Utils.getResource("assets/buttons/btn_finalizar_jogada/btn_finalizar_jogada.png"));
        }

        spriteHolder = new JLabel(disabledButton);
        spriteHolder.setLocation(0, 0);
        spriteHolder.setBounds(0, 0, disabledButton.getIconWidth(), disabledButton.getIconHeight());

        setupMouseEvents();

        return spriteHolder;
    }

    public int getWidth() {
        return disabledButton.getIconWidth();
    }

    public int getHeight() {
        return disabledButton.getIconHeight();
    }

    public void moveTo(int x, int y) {
        spriteHolder.setLocation(x, y);
    }

    @Override
    public void onUpdate(Grid grid, WindowLocation loc, MoveToFront mov, CollisionChecker col) {

    }

    @Override
    public void onDestroy(Grid grid, WindowLocation loc, MoveToFront mov, CollisionChecker col) {

    }

    public void setEnabled() {
        isEnabled = true;
        spriteHolder.setIcon(button);
    }

    public void setDisabled() {
        isEnabled = false;
        spriteHolder.setIcon(disabledButton);
    }

    public void setClickListener(Runnable listener){
        this.clickListener = listener;
    }

    private void setupMouseEvents() {
        spriteHolder.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (isEnabled)
                    spriteHolder.setIcon(downButton);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                if (isEnabled) {
                    spriteHolder.setIcon(button);

                    if(clickListener != null)
                        clickListener.run();
                }
            }
        });
    }
}
