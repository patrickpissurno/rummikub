package rummikub;

import java.util.List;

public abstract class Jogador {
    protected List<Pedra> pedras;

    public abstract void comprarPedra(Pedra pedra, Grid grid);
    public abstract List<Pedra> getPedras();
    abstract List<Conjunto> getConjuntosJogada();
    abstract void fimJogada();

    public void clearPedras() {
        pedras.clear();
    }

    public void addPedra(Pedra pedra) {
        pedras.add(pedra);
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

}
