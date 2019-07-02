package rummikub;

import rummikub.interfaces.CollisionChecker;
import rummikub.interfaces.GameUIs;
import rummikub.interfaces.GerenciadorDeConjuntos;
import rummikub.interfaces.MoveToFront;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JogadorPessoa extends Jogador {

    private static final String MODO_ORDENACAO_MAO = MODO_ORDENACAO_NUMERO_DEPOIS_COR;

    public JogadorPessoa(){
        super();
    }

    public void comprarPedra(Pedra pedra, Grid grid){
        pedra.setConjunto(null);
        pedra.desvirar();
        pedras.add(pedra);

        sortPedras(grid);
    }

    @Override
    public void sortPedras(Grid grid){
        super.sortPedras(grid, MODO_ORDENACAO_MAO);

        // atualiza a parte gráfica
        int maxX = Main.WINDOW_WIDTH / grid.getCellSizeInPx(); //maximo de pedras que cabem na tela horizontalmente

        for(int i = 0; i < pedras.size(); i++){
            final Pedra pedra = pedras.get(i);
            pedra.moveTo(grid, (i % maxX) * grid.getCellSizeInPx(),Main.WINDOW_HEIGHT - grid.getCellSizeInPx() * (1 + i / maxX));

//            System.out.print("(" + pedra.getTipo() + ") ");
        }
//        System.out.println();
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
    public void onInicioDoTurno(Grid grid, MoveToFront mov, CollisionChecker col, GerenciadorDeConjuntos conj, GameUIs ui) {
        //no-op
    }
}
