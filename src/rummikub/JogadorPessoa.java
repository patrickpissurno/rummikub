package rummikub;

import rummikub.interfaces.GameUIs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JogadorPessoa extends Jogador {

    private static final String MODO_ORDENACAO_COR_DEPOIS_NUMERO = "cor_depois_numero";
    private static final String MODO_ORDENACAO_NUMERO_DEPOIS_COR = "numero_depois_cor";

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

    public void sortPedras(Grid grid){
        int maxX = Main.WINDOW_WIDTH / grid.getCellSizeInPx(); //maximo de pedras que cabem na tela horizontalmente

        final List<String> cores = Arrays.asList(Pedra.COR_AZUL, Pedra.COR_VERDE, Pedra.COR_VERMELHO, Pedra.COR_ROSA);

        pedras.sort((a, b) -> {
            int _a = a.getTipo().equals(Pedra.TIPO_CORINGA) ? 500 : Integer.parseInt(a.getTipo());
            int _b = b.getTipo().equals(Pedra.TIPO_CORINGA) ? 500 : Integer.parseInt(b.getTipo());

            //ordena por cor, depois por número
            if(MODO_ORDENACAO_MAO.equals(MODO_ORDENACAO_COR_DEPOIS_NUMERO)) {
                _a += cores.indexOf(a.getCor()) * 100;
                _b += cores.indexOf(b.getCor()) * 100;
            }

            //ordena por número, depois por cor
            if(MODO_ORDENACAO_MAO.equals(MODO_ORDENACAO_NUMERO_DEPOIS_COR)) {
                if (_a == _b)
                    return (cores.indexOf(a.getCor()) + 1) - (cores.indexOf(b.getCor()) + 1);
            }

            return _a - _b;
        });

        for(int i = 0; i < pedras.size(); i++){
            final Pedra pedra = pedras.get(i);
            pedra.moveTo(grid, (i % maxX) * grid.getCellSizeInPx(),Main.WINDOW_HEIGHT - grid.getCellSizeInPx() * (1 + i / maxX));

            System.out.print("(" + pedra.getTipo() + ") ");
        }
        System.out.println();
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
