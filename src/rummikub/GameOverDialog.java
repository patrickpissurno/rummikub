package rummikub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class GameOverDialog {

    private static final int DIALOG_LAYER = JLayeredPane.POPUP_LAYER;

    private JLabel background;
    private JLabel title;
    private JLabel ranking;
    private JLabel jogarDeNovo;

    private Runnable onJogarDeNovoListener;

    public GameOverDialog(){
        background = new JLabel();
        background.setBackground(Color.BLACK);
        background.setOpaque(true);
        background.setBounds(0,0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
        background.setLocation(0, 0);

        // impede a propagação dos cliques
        background.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mousePressed(e);
            }
        });

        title = new JLabel(formatText("Fim de Jogo!", 20));
        title.setForeground(Color.WHITE);
        title.setBounds(0,0, Main.WINDOW_WIDTH, 80);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setLocation(0, 0);

        ranking = new JLabel(formatText("Ranking:", 14));
        ranking.setForeground(Color.WHITE);
        ranking.setBounds(0,0, Main.WINDOW_WIDTH, 80);
        ranking.setHorizontalAlignment(SwingConstants.CENTER);
        ranking.setVerticalAlignment(SwingConstants.CENTER);
        ranking.setLocation(0, 100);

        final Botao b = new Botao(Botao.TIPO_JOGAR_DE_NOVO);
        jogarDeNovo = b.onCreate();
        b.setEnabled();
        b.moveTo(Main.WINDOW_WIDTH /2 - b.getWidth()/2, 400);
        b.setClickListener(() -> jogarDeNovoPressed());
    }

    public void attach(JLayeredPane panel){
        for(JLabel label : getComponents()) {
            panel.add(label, new Integer(panel.getComponentCount() + 1));
            panel.setLayer(label, DIALOG_LAYER);
        }
    }

    public void hide(){
        for(JLabel label : getComponents())
            label.setVisible(false);
    }

    public void show(Jogador vencedor, int pontuacaoVencedor, int pontuacaoPerdedor){
        for(JLabel label : getComponents())
            label.setVisible(true);

        String txt = "Ranking:";
        txt += "<br>Vencedor: " + (vencedor instanceof JogadorPessoa ? "Player" : "CPU") + " com " + pontuacaoVencedor + " pontos";
        txt += "<br>Perdedor: " + (!(vencedor instanceof JogadorPessoa) ? "Player" : "CPU") + " com " + pontuacaoPerdedor + " pontos";

        ranking.setText(formatText(txt, 14));
    }

    public void setOnJogarDeNovoListener(Runnable listener){
        this.onJogarDeNovoListener = listener;
    }

    private void jogarDeNovoPressed(){
        if(onJogarDeNovoListener != null)
            onJogarDeNovoListener.run();
    }

    private JLabel[] getComponents(){
        return new JLabel[]{
                title,
                ranking,
                jogarDeNovo,
                background,
        };
    }

    private String formatText(String text, int fontSize){
        return "<html><span style='font-size:" + fontSize + "px'>" + text + "</span></html>";
    }
}
