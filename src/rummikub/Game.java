package rummikub;

import rummikub.interfaces.CollisionChecker;
import rummikub.interfaces.Jogador;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Game implements CollisionChecker {
    private JLayeredPane panel;
    private Grid grid;

    private List<Pedra> mesa;
    private Stack<Pedra> monteDeCompras;

    private Jogador jogador;
    private Jogador cpu;

    private Jogador turno;

    private FrameWrapper frameWrapper;
    private LayeredPaneWrapper paneWrapper;

    public Game(JLayeredPane panel, FrameWrapper frameWrapper){
        this.frameWrapper = frameWrapper;
        this.panel = panel;
        this.paneWrapper = new LayeredPaneWrapper(panel);

        this.mesa = new ArrayList<>();
        this.monteDeCompras = new Stack<>();

        this.grid = new Grid(55);

        this.jogador = new JogadorPessoa();
        this.cpu = new JogadorCPU();

        inicializaMonteDeCompras();

        inicializaPartida();
    }

    private void inicializaMonteDeCompras(){
        final String[] tipos = new String[]{Pedra.TIPO_NUMERO_1, Pedra.TIPO_NUMERO_2, Pedra.TIPO_NUMERO_3, Pedra.TIPO_NUMERO_4, Pedra.TIPO_NUMERO_5, Pedra.TIPO_NUMERO_6, Pedra.TIPO_NUMERO_7, Pedra.TIPO_NUMERO_8, Pedra.TIPO_NUMERO_9, Pedra.TIPO_NUMERO_10, Pedra.TIPO_NUMERO_11, Pedra.TIPO_NUMERO_12, Pedra.TIPO_NUMERO_13, Pedra.TIPO_CORINGA};
        final String[] cores = new String[]{Pedra.COR_AZUL, Pedra.COR_VERDE, Pedra.COR_VERMELHO, Pedra.COR_ROSA};

        //adiciona duas de cada peça númerica ao monte de compras
        for(int i = 0; i < cores.length * 2; i++) {
            for (int j = 0; j < tipos.length - 1; j++) {
                final Pedra pedra = novaPedra(new Pedra(tipos[j], cores[i % cores.length]));
                pedra.moveTo(grid, 0, 0);

                monteDeCompras.push(pedra);
            }
        }

        //adiciona os dois coringas ao monte de compras
        for(int i = 0; i < 2; i++) {
            final Pedra pedra = novaPedra(new Pedra(Pedra.TIPO_CORINGA));
            pedra.moveTo(grid, 0, 0);

            monteDeCompras.push(pedra);
        }

        //Embaralha as peças
        Collections.shuffle(monteDeCompras);
    }

    private void inicializaPartida(){
        //cada jogador compra 14 peças
        for(int i = 0; i < 14; i++) {
            jogador.comprarPedra(monteDeCompras.pop(), grid);
            cpu.comprarPedra(monteDeCompras.pop(), grid);
        }

        //um jogador é escolhido aleatoriamente para começar
        if(Utils.randomRange(0, 1) == 0)
            turno = jogador;
        else
            turno = cpu;
    }

    //contagem de pontos dos conjuntos da jogada inicial
    private int pontuaJogadaInicial(){
        int pontos = 0;
        List<Conjunto> conjuntos = turno.getConjuntosJogada();

        for (Conjunto conjunto : conjuntos)
            pontos += conjunto.getPontos();

        return pontos;
    }

    //true se jogada inicial é válida
    private boolean validaJogadaInicial() {
        return pontuaJogadaInicial() >= 30;
    }

    public Pedra novaPedra(Pedra pedra){
        final JLabel sprite = pedra.onCreate(grid, frameWrapper, paneWrapper, this);

        //Adiciona o sprite ao frame para que seja renderizado na tela
        panel.add(sprite, new Integer(panel.getComponentCount() + 1));

        return pedra;
    }

    //documentação na interface CollisionChecker
    @Override
    public boolean checkCollision(JLabel me) {
        final Point a = me.getLocation();
        int size = grid.getCellSizeInPx();

        for(Component c : panel.getComponentsInLayer(JLayeredPane.DEFAULT_LAYER)){
            if(c.equals(me))
                continue;

            final Point b = c.getLocation();

            if(a.x < b.x + size && a.x + size > b.x && a.y < b.y + size && a.y + size > b.y)
                return true;
        }
        return false;
    }
}