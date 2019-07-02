package rummikub;

import rummikub.interfaces.GameUIs;

import java.util.ArrayList;
import java.util.List;

public abstract class Jogador {
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

    public abstract void onInicioDoTurno(GameUIs ui);
}
