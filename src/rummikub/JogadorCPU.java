package rummikub;

import rummikub.interfaces.CollisionChecker;
import rummikub.interfaces.GameUIs;
import rummikub.interfaces.GerenciadorDeConjuntos;
import rummikub.interfaces.MoveToFront;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JogadorCPU extends Jogador {

    public JogadorCPU(){
        super();
    }

    @Override
    public void comprarPedra(Pedra pedra, Grid grid) {
        //move a peça para fora da tela
        pedra.moveTo(grid, -grid.getCellSizeInPx(), -grid.getCellSizeInPx());
        
        pedra.desvirar();

        pedras.add(pedra);
    }

    @Override
    public ArrayList<Conjunto> getConjuntosJogada() {
        return new ArrayList<>(conjuntosJogada);
    }

    @Override
    public void fimJogada() {
        conjuntosJogada.clear();
    }

    @Override
    public int getSomatorioMao() {
        return super.getSomatorioMao();
    }

    @Override
    public void onInicioDoTurno(Grid grid, MoveToFront mov, CollisionChecker col, GerenciadorDeConjuntos conj, GameUIs ui) {
        final Timer t = new Timer(1000, (e) -> {
            ConjuntoVirtual melhorJogada = identificaMelhorJogada();

            System.out.println("Melhor Jogada IA: " + (melhorJogada == null ? "NULL" : melhorJogada));

            while (melhorJogada != null){
                final Point espacoLivre = identificaEspacoLivreNoTabuleiro(grid, col, melhorJogada.getPedras().size());
                System.out.println("Espaço livre: " + espacoLivre);

                if (espacoLivre != null) {
                    for (int i = 0; i < melhorJogada.getPedras().size(); i++) {
                        final Pedra pedra = melhorJogada.getPedras().get(i);
                        pedra.simulateDrag(new Point(espacoLivre.x + (i * grid.getCellSizeInPx()), espacoLivre.y), grid, mov, col, conj, ui);
                    }
                }

                melhorJogada = identificaMelhorJogada();
                System.out.println("Melhor Jogada IA: " + (melhorJogada == null ? "NULL" : melhorJogada));
            }

            if (ui.rummikubButtonPressed()) //se conseguiu
                return;

            if (ui.finalizarJogadaButtonPressed()) //se conseguiu
                return;

            ui.passarAVezButtonPressed();
        });
        t.setRepeats(false);
        t.start();
    }

    private Point identificaEspacoLivreNoTabuleiro(Grid grid, CollisionChecker col, int quantidade){
        final Pedra fake = new PedraVirtual(Pedra.TIPO_NUMERO_1);

        for(int y = 0; y < Main.WINDOW_HEIGHT - grid.getCellSizeInPx(); y += grid.getCellSizeInPx()) {
            for (int x = 0; x < Main.WINDOW_WIDTH - grid.getCellSizeInPx(); x += grid.getCellSizeInPx()){
                if((y == 0 && x == 0) || (y == grid.getCellSizeInPx() && x == 0))
                    continue;

                if(x + (grid.getCellSizeInPx() * quantidade) > Main.WINDOW_WIDTH)
                    continue;

                fake.moveTo(grid, x, y);

                boolean ocupado = false;
                for(int i = 0; i < quantidade; i++) {
                    if (col.checkCollision(fake, i * grid.getCellSizeInPx()) != null) {
                        x += i * grid.getCellSizeInPx(); //pula as celulas ja testadas
                        ocupado = true;
                        break;
                    }
                }

                if(ocupado)
                    continue;

                return new Point(fake.getLocation().x + grid.getCellSizeInPx(), fake.getLocation().y);
            }
        }

        return null;
    }

    private ConjuntoVirtual identificaMelhorJogada(){
        final List<Pedra> coringas = new ArrayList<>();
        for(Pedra p : pedras) {
            if(p.getTipo().equals(Pedra.TIPO_CORINGA))
                coringas.add(p);
        }

        List<ConjuntoVirtual> jogadasValidas = identificaJogadasValidasGrupos(coringas);
        jogadasValidas.addAll(identificaJogadasValidasSequencia(coringas));

        // ordena as jogadas da melhor para a pior
        jogadasValidas.sort((a, b) -> b.getPontos() - a.getPontos());

        if(jogadasValidas.isEmpty())
            return null;

        return jogadasValidas.get(0);
    }

    private List<ConjuntoVirtual> identificaJogadasValidasSequencia(List<Pedra> coringas){
        sortPedras(null, Jogador.MODO_ORDENACAO_COR_DEPOIS_NUMERO);
        Collections.reverse(pedras);

        final List<ConjuntoVirtual> jogadasValidas = new ArrayList<>();
        for(int j = 0; j < pedras.size(); j++) {
            final List<ConjuntoVirtual> jogadasAlternativas = new ArrayList<>();

            String cor = "";
            int coringasUsados = 0;

            for (int i = j; i < pedras.size(); i++) {
                final Pedra p = pedras.get(i);

                if (p.getTipo().equals(Pedra.TIPO_CORINGA))
                    continue;

                if (!p.getCor().equals(cor)) {
                    cor = p.getCor();

                    final ConjuntoVirtual c = new ConjuntoVirtual(new ArrayList<>());
                    c.add(p);
                    jogadasAlternativas.add(c);
                    coringasUsados = 0;
                } else {
                    final ConjuntoVirtual c = jogadasAlternativas.get(jogadasAlternativas.size() - 1);
                    final Pedra anterior = c.getPedras().get(c.getPedras().size() - 1);

                    int coringasDisponiveis = coringas.size() - coringasUsados;

                    // impede que forme sequencias com números duplicados
                    final boolean duplicado = anterior.getTipo().equals(p.getTipo());
                    if (duplicado) {
                        i -= 1;
                        cor = "";
                        continue;
                    }

                    boolean valido = false;

                    int diff = Integer.parseInt(anterior.getTipo()) - Integer.parseInt(p.getTipo());
                    if (diff > 1 && diff - coringasDisponiveis <= 1) { //se a diferença for maior que 1, mas a gente tem coringas disponíveis, vamos usá-los
                        coringasUsados += diff - 1;
                        if (coringasUsados > coringas.size())
                            coringasUsados = coringas.size();

                        for(int z = 0; z < diff - 1 && z < coringas.size(); z++)
                            c.add(coringas.get(z));

                        valido = true;
                    } else if (diff == 1)
                        valido = true;

                    if (!valido){
                        i -= 1;
                        cor = "";
                        continue;
                    }

                    c.add(p);
                }
            }

            for(ConjuntoVirtual c : jogadasAlternativas){
                if(c.getPontos() < 1)
                    continue;

                jogadasValidas.add(c);
            }
        }

        return jogadasValidas;
    }

    private List<ConjuntoVirtual> identificaJogadasValidasGrupos(List<Pedra> coringas){
        sortPedras(null, Jogador.MODO_ORDENACAO_NUMERO_DEPOIS_COR);

        final List<ConjuntoVirtual> jogadas = new ArrayList<>();

        String tipo = "";
        for(Pedra p : pedras) {
            if(p.getTipo().equals(Pedra.TIPO_CORINGA))
                continue;

            if(!p.getTipo().equals(tipo)){
                tipo = p.getTipo();

                final ConjuntoVirtual c = new ConjuntoVirtual(new ArrayList<>());
                c.add(p);
                jogadas.add(c);
            }
            else {
                final ConjuntoVirtual c = jogadas.get(jogadas.size() - 1);

                // impede que forme grupos com números de cores duplicadas
                boolean found = false;
                for(Pedra p1 : c.getPedras()){
                    if(p1.getCor().equals(p.getCor())) {
                        found = true;
                        break;
                    }
                }

                if(!found)
                    c.add(p);
            }
        }

        final List<ConjuntoVirtual> jogadasValidas = new ArrayList<>();
        for(ConjuntoVirtual c : jogadas){
            if(c.getPontos() < 1)
                continue;

            jogadasValidas.add(c);

            // computa as jogadas extras que podem ser feitas adicionando os coringas à essa jogada
            for(int i = 0; i < coringas.size(); i++){
                final ConjuntoVirtual jogadaExtra = new ConjuntoVirtual(c.getPedras());
                for(Pedra p : coringas.subList(0, i))
                    jogadaExtra.add(p);

                if(jogadaExtra.getPontos() > c.getPontos())
                    jogadasValidas.add(c);
            }
        }

        return jogadasValidas;
    }

    @Override
    public void sortPedras(Grid grid, String ordem){
        super.sortPedras(grid, ordem);

        // log
        for(Pedra p : pedras)
            System.out.print("(" + p.getTipo() + (p.getTipo().equals(Pedra.TIPO_CORINGA) ? "" : (":" + p.getCor().substring(0, 1) + new StringBuilder(p.getCor()).reverse().substring(0, 1))) + ") ");
        System.out.println();
    }
}
