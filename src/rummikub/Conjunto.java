package rummikub;

import java.util.*;

public class Conjunto {
    private LinkedList<Pedra> pedras;
    private boolean isNew;

    public Conjunto(LinkedList<Pedra> pedras){
        isNew = true;
        this.pedras = pedras;
        sincronizaOrdenacaoTabuleiro();
    }

    public Conjunto(List<Pedra> pedras){
        isNew = true;
        this.pedras = new LinkedList<Pedra>(pedras);
        sincronizaOrdenacaoTabuleiro();
    }

    public boolean isGrupo(){
        sincronizaOrdenacaoTabuleiro();

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
        sincronizaOrdenacaoTabuleiro();

        if (pedras.size() < 3)
            return false;

        String corDoGrupo = pedras.getFirst().getCor();
        ArrayList<Integer> numeros = new ArrayList<>();
        int nCoringas = 0;

        for(int i = 0; i < pedras.size(); i++) {
            // verifica se tem alguma pedra de cor diferente
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
        sincronizaOrdenacaoTabuleiro();

        ArrayList<Conjunto> conjuntos = new ArrayList<>();
        int i = pedras.indexOf(pedraASerRemovida);
        conjuntos.add(new Conjunto(pedras.subList(0, i)));
        conjuntos.add(new Conjunto(pedras.subList(i + 1, pedras.size())));

        return conjuntos;
    }

    // RETORNA NULL caso não tenha split
    public ArrayList<Conjunto> movePedra(Pedra pedra, Conjunto conjuntoDestino){
        sincronizaOrdenacaoTabuleiro();

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
        sincronizaOrdenacaoTabuleiro();
    }

    // usado para contagem de pontos da jogada inicial
    public int getPontos() {
        if (isGrupo()){
            // valor das pedras do grupo * quantidade de pedras
            for (int i = 0; i < pedras.size(); i++)
                if (!pedras.get(i).getTipo().equals(Pedra.TIPO_CORINGA))
                    return Integer.parseInt(pedras.get(i).getTipo()) * pedras.size();
        }
        else if (isSequencia()){
            int primeiroValor = 0;
            int ultimoValor = 0;

            // e.g. pedras[0] = coringa e pedras[1] = 2, a primeira pedra é 1
            for (int i = 0; i < pedras.size(); i++)
                if (!pedras.get(i).getTipo().equals(Pedra.TIPO_CORINGA))
                    primeiroValor = Integer.parseInt(pedras.get(i).getTipo()) - i;

            // e.g. pedras[n] = coringa e pedras[n-1] = 2, a ultima pedra é 3
            for (int i = pedras.size() - 1; i >= 0; i--)
                if (!pedras.get(i).getTipo().equals(Pedra.TIPO_CORINGA))
                    ultimoValor = Integer.parseInt(pedras.get(i).getTipo()) + (pedras.size() - i);

            return ((primeiroValor + ultimoValor) * pedras.size())/2;
        }
        return 0;
    }

    private void sincronizaOrdenacaoTabuleiro(){
        pedras.sort(Comparator.comparingInt(pedra -> pedra.getLocation().x));
    }

    public int size(){
        return pedras.size();
    }

    /**
     * verifica se pode realizar o drag na jogada inicial
     * @return boolean indicando se é possivel realizar o drag
     */
    public boolean isNew() {
        return isNew;
    }

    public void setOld() {
        isNew = false;
    }
}
