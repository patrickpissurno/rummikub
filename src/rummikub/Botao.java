package rummikub;

import rummikub.interfaces.CollisionChecker;
import rummikub.interfaces.GameObject;
import rummikub.interfaces.MoveToFront;
import rummikub.interfaces.WindowLocation;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Botao implements GameObject {

    private JLabel spriteHolder;

    private String tipo;
    private Game game;

    private ImageIcon disabledButton;
    private ImageIcon downButton;
    private ImageIcon button;

    private boolean isEnabled;

    public static final String TIPO_PASSAR_A_VEZ = "passar_vez";

    public Botao(String tipo, Game game) {
        this.tipo = tipo;
        this.game = game;
    }

    @Override
    public JLabel onCreate(Grid grid, WindowLocation loc, MoveToFront mov, CollisionChecker col) {
        if (tipo.equals(TIPO_PASSAR_A_VEZ)) {
            disabledButton = new ImageIcon(Utils.getResource("assets/buttons/btn_passar_a_vez/btn_passar_a_vez_disabled.png"));
            downButton = new ImageIcon(Utils.getResource("assets/buttons/btn_passar_a_vez/btn_passar_a_vez_down.png"));
            button = new ImageIcon(Utils.getResource("assets/buttons/btn_passar_a_vez/btn_passar_a_vez.png"));
        }

        spriteHolder = new JLabel(disabledButton);
        spriteHolder.setLocation(0, 0);
        spriteHolder.setBounds(0, 0, disabledButton.getIconWidth(), disabledButton.getIconHeight());

        setupMouseEvents(grid, loc, mov, col);

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

    private void setupMouseEvents(Grid grid, WindowLocation loc, MoveToFront mov, CollisionChecker col) {
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
                    game.jogadorAtualCompraPedra();
                    game.passaVez();
                }
            }
        });
    }
}
