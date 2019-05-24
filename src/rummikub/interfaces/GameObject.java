package rummikub.interfaces;

import rummikub.Grid;

import javax.swing.*;

public interface GameObject {
    /**
     * Método chamado ao instanciar o GameObject
     * @param grid instância da classe Grid
     * @return
     */
    JLabel onCreate(Grid grid, WindowLocation loc, MoveToFront mov, CollisionChecker col);


    /**
     * Método chamado no loop do jogo (60x por segundo)
     * @param grid instância da classe Grid
     */
    void onUpdate(Grid grid, WindowLocation loc, MoveToFront mov, CollisionChecker col);


    /**
     * Método chamado quando a instância do GameObject for destruída
     * @param grid instância da classe Grid
     */
    void onDestroy(Grid grid, WindowLocation loc, MoveToFront mov, CollisionChecker col);
}
