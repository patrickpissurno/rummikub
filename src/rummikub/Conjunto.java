package rummikub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Conjunto {
    private LinkedList<Pedra> pedras;

    public Conjunto(LinkedList<Pedra> pedras){
        this.pedras = pedras;
    }

    public Conjunto(List<Pedra> pedras){
        this.pedras = new LinkedList<Pedra>(pedras);
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

    public boolean isSequencia(){
        if (pedras.size() < 3)
            return false;

        String corDoGrupo = pedras.getFirst().getCor();
        ArrayList<Integer> numeros = new ArrayList<>();
        int nCoringas = 0;

        for(int i = 0; i < pedras.size(); i++) {
            // verifica se tem alguma pedra de numero diferente
            if (!pedras.get(i).getTipo().equals(Pedra.TIPO_CORINGA) && !pedras.get(i).getCor().equals(corDoGrupo))
                return false;

            if (pedras.get(i).getTipo().equals(Pedra.TIPO_CORINGA))
                nCoringas++;
            else
                numeros.add(Integer.parseInt(pedras.get(i).getTipo()));
        }

        Collections.sort(numeros);

        // ultimo - primeiro <= tamanho - 1 + nCoringas
        // < para os casos em que o coringa está na borda
        // == para os casos em que o coringa está no meio ou não existe
        if (numeros.get(numeros.size() - 1) - numeros.get(0) <= numeros.size() - 1 + nCoringas)
            return true;

        return false;
    }

    public ArrayList<Conjunto> split(Pedra pedraASerRemovida){
        ArrayList<Conjunto> conjuntos = new ArrayList<>();
        int i = pedras.indexOf(pedraASerRemovida);
        conjuntos.add(new Conjunto(pedras.subList(0, i)));
        conjuntos.add(new Conjunto(pedras.subList(i + 1, pedras.size())));

        return conjuntos;
    }

    // RETORNA NULL caso não tenha split
    public ArrayList<Conjunto> movePedra(Pedra pedra, Conjunto conjuntoDestino){
        // caso de borda - não precisa de split
        if (pedras.indexOf(pedra) == pedras.size() - 1 || pedras.indexOf(pedra) == 0) {
            pedras.remove(pedra);
            conjuntoDestino.add(pedra);
            return null;
        }
        else {
            conjuntoDestino.add(pedra);
            return split(pedra);
        }
    }

    public void add(Pedra pedra){
        pedras.add(pedra);
    }

    public int size(){
        return pedras.size();
    }
}
