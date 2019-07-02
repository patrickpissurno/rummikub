package rummikub;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Botao{

    private JLabel spriteHolder;

    private String tipo;

    private ImageIcon disabledButton;
    private ImageIcon downButton;
    private ImageIcon button;

    private boolean isEnabled;

    public static final String TIPO_PASSAR_A_VEZ = "passar_a_vez";
    public static final String TIPO_RUMMIKUB = "rummikub";
    public static final String TIPO_FINALIZAR_JOGADA = "finalizar_jogada";
    public static final String TIPO_JOGAR_DE_NOVO = "jogar_de_novo";

    private Runnable clickListener;

    public Botao(String tipo) {
        this.tipo = tipo;
    }

    public JLabel onCreate() {
        disabledButton = new ImageIcon(Utils.getResource("assets/buttons/btn_" + tipo + "/btn_" + tipo + "_disabled.png"));
        downButton = new ImageIcon(Utils.getResource("assets/buttons/btn_" + tipo + "/btn_" + tipo + "_down.png"));
        button = new ImageIcon(Utils.getResource("assets/buttons/btn_" + tipo + "/btn_" + tipo + ".png"));

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
