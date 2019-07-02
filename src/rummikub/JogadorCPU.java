package rummikub;

import rummikub.interfaces.GameUIs;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class JogadorCPU extends Jogador {

    public JogadorCPU(){
        super();
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

    @Override
    public int getSomatorioMao() {
        return super.getSomatorioMao();
    }

    @Override
    public void onInicioDoTurno(GameUIs ui) {
        final Timer t = new Timer(4000, (e) -> {
            ui.passarAVezButtonPressed();
        });
        t.setRepeats(false);
        t.start();
    }
}
