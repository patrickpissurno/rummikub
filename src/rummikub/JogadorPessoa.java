package rummikub;

import rummikub.interfaces.GameUIs;

import java.util.ArrayList;
import java.util.List;

public class JogadorPessoa extends Jogador {

    public JogadorPessoa(){
        super();
    }

    public void comprarPedra(Pedra pedra, Grid grid){
        int maxX = Main.WINDOW_WIDTH / grid.getCellSizeInPx(); //maximo de pedras que cabem na tela horizontalmente

        //move a pedra para a área onde fica a mão do jogador
        pedra.moveTo(grid, (pedras.size() % maxX) * grid.getCellSizeInPx(),Main.WINDOW_HEIGHT - grid.getCellSizeInPx() * (1 + pedras.size() / maxX));

        pedra.desvirar();

        pedras.add(pedra);
    }

    public List<Pedra> getPedras(){
        return new ArrayList<>(pedras);
    }

    @Override
    public ArrayList<Conjunto> getConjuntosJogada() {
        return new ArrayList<>(conjuntosJogada);
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
        //no-op
    }
}
