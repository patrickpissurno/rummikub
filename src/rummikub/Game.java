package rummikub;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Game {
    private JLayeredPane panel;
    private Grid grid;

    private List<Pedra> mesa;
    private Stack<Pedra> monteDeCompras;

    private Jogador jogador;
    private Jogador cpu;

    public Game(JLayeredPane panel){
        this.panel = panel;
        this.mesa = new ArrayList<>();
        this.monteDeCompras = new Stack<>();

        this.grid = new Grid(55);

        this.jogador = new Jogador();
        this.cpu = new Jogador();

        inicializaMonteDeCompras();

        //exemplo de como comprar uma pedra
        jogador.comprarPedra(monteDeCompras.pop(), grid);
    }

    private void inicializaMonteDeCompras(){
        final String[] tipos = new String[]{Pedra.TIPO_NUMERO_1, Pedra.TIPO_NUMERO_2, Pedra.TIPO_NUMERO_3, Pedra.TIPO_NUMERO_4, Pedra.TIPO_NUMERO_5, Pedra.TIPO_NUMERO_6, Pedra.TIPO_NUMERO_7, Pedra.TIPO_NUMERO_8, Pedra.TIPO_NUMERO_9, Pedra.TIPO_NUMERO_10, Pedra.TIPO_NUMERO_11, Pedra.TIPO_NUMERO_12, Pedra.TIPO_NUMERO_13, Pedra.TIPO_CORINGA};
        final String[] cores = new String[]{Pedra.COR_AZUL, Pedra.COR_VERDE, Pedra.COR_VERMELHO, Pedra.COR_ROSA};

        for(int i = 0; i < cores.length * 2; i++) {
            for (int j = 0; j < tipos.length - 1; j++) {
                final Pedra pedra = novaPedra(new Pedra(tipos[j], cores[i % cores.length]));
                pedra.moveTo(grid, 0, 0);

                monteDeCompras.push(pedra);
            }
        }

        for(int i = 0; i < 2; i++) {
            final Pedra pedra = novaPedra(new Pedra(Pedra.TIPO_CORINGA));
            pedra.moveTo(grid, 0, 0);

            monteDeCompras.push(pedra);
        }

        //Falta embaralhar as peças
    }

    public Pedra novaPedra(Pedra pedra){
        final JLabel sprite = pedra.onCreate(grid);

        //Adiciona o sprite ao frame para que seja renderizado na tela
        panel.add(sprite, new Integer(panel.getComponentCount() + 1));

        return pedra;
    }
}