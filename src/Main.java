import javax.swing.*;
import java.awt.*;

/***
 * Classe responsável por inicializar o contexto gráfico e passar a referência para classe Game
 */
public class Main {
    public static void main(String args[]){
        javax.swing.SwingUtilities.invokeLater(() -> init());
    }

    private static void init(){
        //Inicializa a interface gráfica
        final JFrame frame = new JFrame("Rummikub");
        frame.setMinimumSize(new Dimension(1280, 720));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final JLayeredPane panel = new JLayeredPane();
        frame.setContentPane(panel);
        panel.setLayout(null);
        panel.requestFocus();

        //Mostra a janela
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        //Passa a referência da janela para a classe Game
        final Game game = new Game(panel);
    }
}