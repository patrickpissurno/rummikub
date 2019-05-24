package rummikub;

import javax.swing.*;
import java.awt.*;

/***
 * Classe responsável por inicializar o contexto gráfico e passar a referência para classe rummikub.Game
 */
public class Main {
    public static void main(String args[]){
        javax.swing.SwingUtilities.invokeLater(() -> init());
    }

    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 679;

    private static void init(){
        //Inicializa a interface gráfica
        final JFrame frame = new JFrame("Rummikub");
        frame.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JLayeredPane panel = new JLayeredPane();
        frame.setContentPane(panel);
        panel.setLayout(null);
        panel.requestFocus();

        //Mostra a janela
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setBackground(new Color(0xCFC6B8)); //cor hexadecimal padrão, só adicionar 0x na frente

        //Passa a referência da janela para a classe rummikub.Game
        final Game game = new rummikub.Game(panel, new FrameWrapper(frame));
    }
}