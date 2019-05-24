package rummikub.interfaces;

import javax.swing.*;

public interface CollisionChecker {

    /** Verifica se este JLabel está colidindo com os outros **/
    boolean checkCollision(JLabel me);
}
