package rummikub;

import rummikub.interfaces.CollisionChecker;
import rummikub.interfaces.GameUIs;
import rummikub.interfaces.GerenciadorDeConjuntos;
import rummikub.interfaces.MoveToFront;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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
        final Timer t = new Timer(4000, (e) -> {
            final ConjuntoVirtual melhorJogada = identificaMelhorJogada();
            System.out.println("Melhor Jogada IA: " + (melhorJogada == null ? "NULL" : melhorJogada));

            if(melhorJogada != null){

                if(!ui.isJogadaInicial() || melhorJogada.getPontos() >= 30) {
                    final Point espacoLivre = identificaEspacoLivreNoTabuleiro(grid, col, melhorJogada.getPedras().size());
                    System.out.println("Espaço livre: " + espacoLivre);
                    if (espacoLivre != null) {
                        for (int i = 0; i < melhorJogada.getPedras().size(); i++) {
                            final Pedra pedra = melhorJogada.getPedras().get(i);
                            pedra.simulateDrag(new Point(espacoLivre.x + (i * grid.getCellSizeInPx()), espacoLivre.y), grid, mov, col, conj, ui);
                        }

                        if (ui.finalizarJogadaButtonPressed()) //se conseguiu
                            return;
                    }
                }

            }

            ui.passarAVezButtonPressed();
        });
        t.setRepeats(false);
        t.start();
    }

    private Point identificaEspacoLivreNoTabuleiro(Grid grid, CollisionChecker col, int quantidade){
        final Pedra fake = new PedraVirtual(Pedra.TIPO_NUMERO_1);

        for(int y = 0; y < Main.WINDOW_HEIGHT - grid.getCellSizeInPx(); y += grid.getCellSizeInPx()) {
            for (int x = 0; x < Main.WINDOW_WIDTH - grid.getCellSizeInPx(); x += grid.getCellSizeInPx()){
                if(y == 0 && (x == 0 || x == grid.getCellSizeInPx()))
                    continue;

                if(x + (grid.getCellSizeInPx() * quantidade) > Main.WINDOW_WIDTH)
                    continue;

                fake.moveTo(grid, x, y);

                boolean ocupado = false;
                for(int i = 0; i < quantidade; i++) {
                    if (col.checkCollision(fake, i * grid.getCellSizeInPx()) != null) {
                        x += (i + 1) * grid.getCellSizeInPx(); //pula as celulas ja testadas
                        ocupado = true;
                        break;
                    }
                }

                if(ocupado)
                    continue;

                return fake.getLocation();
            }
        }

        return null;
    }

    private ConjuntoVirtual identificaMelhorJogada(){
        sortPedras(null, Jogador.MODO_ORDENACAO_NUMERO_DEPOIS_COR);

        final List<Pedra> coringas = new ArrayList<>();
        for(Pedra p : pedras) {
            if(p.getTipo().equals(Pedra.TIPO_CORINGA))
                coringas.add(p);
        }

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

        // ordena as jogadas da melhor para a pior
        jogadasValidas.sort((a, b) -> b.getPontos() - a.getPontos());

        if(jogadasValidas.isEmpty())
            return null;

        return jogadasValidas.get(0);
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
