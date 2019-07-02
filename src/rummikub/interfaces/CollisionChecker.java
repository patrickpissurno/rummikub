package rummikub.interfaces;

import rummikub.Pedra;

public interface CollisionChecker {

    /** Verifica se esta Pedra está colidindo com os outras **/
    Pedra checkCollision(Pedra me, int offsetX);
}
