package rummikub;

import rummikub.interfaces.GerenciadorDeConjuntos;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameSnapshot {
    private List<Pedra> pedras;
    private List<Point> positions;
    private List<Conjunto> conjuntos;

    public GameSnapshot(List<Pedra> todasAsPedras, List<Conjunto> mesa){
        this.pedras = todasAsPedras;
        this.conjuntos = new ArrayList<>(mesa);

        this.positions = new ArrayList<>();
        for(Pedra p : pedras)
            positions.add(new Point(p.getLocation()));
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
        }

        // restaura os conjuntos originais
        for(Conjunto c : conjuntos)
            conj.addConjunto(new Conjunto(c.getPedras()));

    }
}
