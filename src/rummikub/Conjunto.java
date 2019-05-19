package rummikub;

import java.util.LinkedList;
import java.util.List;

public class Conjunto {
    private LinkedList<Pedra> pedras;

    public Conjunto(LinkedList<Pedra> pedras){
        this.pedras = pedras;
    }

    public boolean isGrupo(){
        if(pedras.size() < 3 || pedras.size() > 4)
            return false;

        // a.k.a. numero do grupo
        String tipoDoGrupo = pedras.getFirst().getTipo();

        LinkedList<String> cores = new LinkedList<>();

        for(int i = 0; i < pedras.size(); i++) {
            // verifica se tem alguma pedra de numero diferente
            if (!pedras.get(i).getTipo().equals(Pedra.TIPO_CORINGA) && !pedras.get(i).getTipo().equals(tipoDoGrupo))
                return false;

            for (int j = 0; j < cores.size(); j++)
                // cor repetida
                if (cores.get(j).equals(pedras.get(i).getCor())){
                    return false;
                }

            cores.add(pedras.get(i).getCor());
        }

        return true;
    }
}
