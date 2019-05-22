package rummikub;

import rummikub.interfaces.Jogador;

import java.util.ArrayList;
import java.util.List;

public class JogadorCPU implements Jogador {
    private List<Pedra> pedras;

    private List<Conjunto> conjuntosJogada;

    public JogadorCPU(){
        pedras = new ArrayList<>();
    }

    @Override
    public void comprarPedra(Pedra pedra, Grid grid) {
        //move a peça para fora da tela
        pedra.moveTo(grid, -grid.getCellSizeInPx(), -grid.getCellSizeInPx());
        
        pedra.desvirar();

        pedras.add(pedra);
    }

    @Override
    public List<Pedra> getPedras() {
        return pedras;
    }

    @Override
    public ArrayList<Conjunto> getConjuntosJogada() {
        return new ArrayList<Conjunto>(conjuntosJogada);
    }

    @Override
    public void fimJogada() {
        conjuntosJogada.clear();
    }
}
