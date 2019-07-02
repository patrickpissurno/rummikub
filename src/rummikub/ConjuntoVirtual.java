package rummikub;

import java.util.LinkedList;
import java.util.List;

public class ConjuntoVirtual extends Conjunto {
    public ConjuntoVirtual(LinkedList<Pedra> pedras) {
        super(pedras);
    }

    public ConjuntoVirtual(List<Pedra> pedras) {
        super(pedras);
    }

    @Override
    protected void sincronizaOrdenacaoTabuleiro(){
        // no-op
    }
}
