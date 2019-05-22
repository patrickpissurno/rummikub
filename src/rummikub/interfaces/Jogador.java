package rummikub.interfaces;

import rummikub.Conjunto;
import rummikub.Grid;
import rummikub.Pedra;

import java.util.LinkedList;
import java.util.List;

public interface Jogador {
    void comprarPedra(Pedra pedra, Grid grid);
    List<Pedra> getPedras();
    List<Conjunto> getConjuntosJogada();
    void fimJogada();
}
