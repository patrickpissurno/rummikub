package rummikub;

import rummikub.interfaces.*;

import javax.swing.*;

public class PedraVirtual extends Pedra {
    public PedraVirtual(String tipo, String cor) {
        super(tipo, cor);
        init();
    }

    public PedraVirtual(String tipo) {
        super(tipo);
        init();
    }

    private void init(){
        onCreate(null, null, null, null, null, null);
    }

    @Override
    public JLabel onCreate(Grid grid, WindowLocation loc, MoveToFront mov, CollisionChecker col, GerenciadorDeConjuntos conj, GameUIs ui) {
        spriteHolder =  new JLabel();
        spriteHolder.setLocation(0, 0);
        return spriteHolder;
    }
}
