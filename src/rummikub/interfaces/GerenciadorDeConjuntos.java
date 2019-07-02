package rummikub.interfaces;

import rummikub.Conjunto;

import java.util.List;

public interface GerenciadorDeConjuntos {
    List<Conjunto> getConjuntos();
    void addConjunto(Conjunto c);
    void removeConjunto(Conjunto c);
}
