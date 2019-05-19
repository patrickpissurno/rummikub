package rummikub.interfaces;

import rummikub.Grid;
import rummikub.Pedra;

import java.util.List;

public interface Jogador {
    void comprarPedra(Pedra pedra, Grid grid);
    List<Pedra> getPedras();
}
