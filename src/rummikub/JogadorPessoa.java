package rummikub;

import rummikub.interfaces.Jogador;

import java.util.ArrayList;
import java.util.List;

public class JogadorPessoa implements Jogador {
    private List<Pedra> pedras;

    public JogadorPessoa(){
        this.pedras = new ArrayList<>();
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
}
