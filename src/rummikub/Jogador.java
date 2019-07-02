package rummikub;

import rummikub.interfaces.CollisionChecker;
import rummikub.interfaces.GameUIs;
import rummikub.interfaces.GerenciadorDeConjuntos;
import rummikub.interfaces.MoveToFront;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Jogador {

    public static final String MODO_ORDENACAO_COR_DEPOIS_NUMERO = "cor_depois_numero";
    public static final String MODO_ORDENACAO_NUMERO_DEPOIS_COR = "numero_depois_cor";

    protected List<Pedra> pedras;
    protected List<Integer> pontuacaoPartidas;
    protected List<Conjunto> conjuntosJogada;

    public Jogador(){
        this.pontuacaoPartidas = new ArrayList<>();
        this.pedras = new ArrayList<>();
        this.conjuntosJogada = new ArrayList<>();
    }

    public abstract void comprarPedra(Pedra pedra, Grid grid);
    abstract List<Conjunto> getConjuntosJogada();
    abstract void fimJogada();

    public void clearPedras() {
        pedras.clear();
    }

    public void addPedra(Pedra pedra) {
        pedras.add(pedra);
    }

    public void removePedra(Pedra pedra){
        pedras.remove(pedra);
    }

    public List<Pedra> getPedras(){
        return new ArrayList<>(pedras);
    }

    public void sortPedras(Grid grid){
        sortPedras(grid, "");
    }

    public void sortPedras(Grid grid, String ordem){
        final List<String> cores = Arrays.asList(Pedra.COR_AZUL, Pedra.COR_VERDE, Pedra.COR_VERMELHO, Pedra.COR_ROSA);

        pedras.sort((a, b) -> {
            int _a = a.getTipo().equals(Pedra.TIPO_CORINGA) ? 500 : Integer.parseInt(a.getTipo());
            int _b = b.getTipo().equals(Pedra.TIPO_CORINGA) ? 500 : Integer.parseInt(b.getTipo());

            //ordena por cor, depois por número
            if(ordem.equals(MODO_ORDENACAO_COR_DEPOIS_NUMERO)) {
                _a += cores.indexOf(a.getCor()) * 100;
                _b += cores.indexOf(b.getCor()) * 100;
            }

            //ordena por número, depois por cor
            if(ordem.equals(MODO_ORDENACAO_NUMERO_DEPOIS_COR)) {
                if (_a == _b)
                    return (cores.indexOf(a.getCor()) + 1) - (cores.indexOf(b.getCor()) + 1);
            }

            return _a - _b;
        });
    }

    public int getSomatorioMao(){
        int somatorio = 0;
        for (Pedra pedra : pedras) {
            if (pedra.getTipo().equals(Pedra.TIPO_CORINGA))
                somatorio += 30;
            else
                somatorio += Integer.parseInt(pedra.getTipo());
        }
        return somatorio;
    }

    public void addPontuacaoPartida(int pontuacao) {
        pontuacaoPartidas.add(pontuacao);
    }

    public List<Integer> getPontuacoesPartidas() {
        return pontuacaoPartidas;
    }

    public int getPontuacaoPartida(int index) {
        return pontuacaoPartidas.get(index);
    }

    public int getSomaPontuacaoPartida() {
        int soma = 0;
        for (Integer pontuacaoPartida : pontuacaoPartidas)
            soma += pontuacaoPartida;

        return soma;
    }

    public abstract void onInicioDoTurno(Grid grid, MoveToFront mov, CollisionChecker col, GerenciadorDeConjuntos conj, GameUIs ui);
}
