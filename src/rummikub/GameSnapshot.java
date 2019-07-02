package rummikub;

import rummikub.interfaces.GerenciadorDeConjuntos;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameSnapshot {
    private List<Pedra> pedras;
    private List<Point> positions;
    private List<Conjunto> conjuntos;
    private Jogador jogador;
    private List<Pedra> maoDoJogador;

    public GameSnapshot(List<Pedra> todasAsPedras, List<Conjunto> mesa, Jogador jogador){
        this.pedras = todasAsPedras;
        this.conjuntos = new ArrayList<>(mesa);
        this.maoDoJogador = new ArrayList<>(jogador.getPedras());

        this.positions = new ArrayList<>();
        for(Pedra p : pedras)
            positions.add(new Point(p.getLocation()));

        this.jogador = jogador;
    }

    public void restore(Grid grid, GerenciadorDeConjuntos conj){

        // remove todos os conjuntos atuais
        final List<Conjunto> old = conj.getConjuntos();
        for(int i = 0; i < old.size(); i++)
            conj.removeConjunto(old.get(i));

        // reseta as pedras para suas posicoes originais
        for(int i = 0; i < pedras.size(); i++) {
            final Point point = positions.get(i);
            pedras.get(i).moveTo(grid, point.x, point.y);
            pedras.get(i).setConjunto(null);
        }

        // restaura os conjuntos originais
        for(Conjunto c : conjuntos)
            conj.addConjunto(new Conjunto(c.getPedras()));

        jogador.clearPedras();
        for(Pedra p : maoDoJogador)
            jogador.addPedra(p);
    }
}
