package rummikub;

import rummikub.interfaces.CollisionChecker;
import rummikub.interfaces.GameUIs;
import rummikub.interfaces.GerenciadorDeConjuntos;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Game implements CollisionChecker, GameUIs, GerenciadorDeConjuntos {
    private JLayeredPane panel;
    private Grid grid;

    private FrameWrapper frameWrapper;
    private LayeredPaneWrapper paneWrapper;

    private Botao botaoPassarVez;
    private Botao botaoRummikub;
    private Botao botaoFinalizarJogada;

    private List<Pedra> todasAsPedras;
    private List<Conjunto> mesa;
    private Stack<Pedra> monteDeCompras;

    private Jogador jogador;
    private Jogador cpu;
    private boolean jogadorInicial;
    private boolean cpuInicial;

    private Jogador turno;
    private boolean turnoInicial;

    private int somatorioDaMesa;

    private GameSnapshot snapshot;

    public Game(JLayeredPane panel, FrameWrapper frameWrapper){
        this.frameWrapper = frameWrapper;
        this.panel = panel;
        this.paneWrapper = new LayeredPaneWrapper(panel);

        this.todasAsPedras = new ArrayList<>();
        this.mesa = new ArrayList<>();
        this.monteDeCompras = new Stack<>();

        this.grid = new Grid(55);

        this.jogador = new JogadorPessoa();
        this.cpu = new JogadorCPU();
        this.jogadorInicial = true;
        this.cpuInicial = true;
        this.turnoInicial = true;

        inicializaBotoes();

        inicializaMonteDeCompras();

        inicializaPartida();
    }

    private void inicializaBotoes() {
        botaoPassarVez = novoBotao(new Botao(Botao.TIPO_PASSAR_A_VEZ));
        int botaoPassarVezY = panel.getHeight()/2 - 64 - botaoPassarVez.getHeight();
        botaoPassarVez.moveTo(panel.getWidth() - 8 - botaoPassarVez.getWidth(), botaoPassarVezY);
        botaoPassarVez.setClickListener(() -> passarAVezButtonPressed());

        botaoRummikub = novoBotao(new Botao(Botao.TIPO_RUMMIKUB));
        int botaoRummikubY = botaoPassarVezY - 8 - botaoRummikub.getHeight();
        botaoRummikub.moveTo(panel.getWidth() - 8 - botaoRummikub.getWidth(), botaoRummikubY);
        botaoRummikub.setClickListener(() -> rummikubButtonPressed());

        botaoFinalizarJogada = novoBotao(new Botao(Botao.TIPO_FINALIZAR_JOGADA));
        int botaoFinalizarJogadaY = botaoRummikubY - 8 - botaoFinalizarJogada.getHeight();
        botaoFinalizarJogada.moveTo(panel.getWidth() - 8 - botaoFinalizarJogada.getWidth(), botaoFinalizarJogadaY);
        botaoFinalizarJogada.setClickListener(() -> finalizarJogadaButtonPressed());
    }

    private Botao novoBotao(Botao botao) {
        final JLabel sprite = botao.onCreate(grid, frameWrapper, paneWrapper, this, this);

        //Adiciona o sprite ao frame para que seja renderizado na tela
        panel.add(sprite, new Integer(panel.getComponentCount() + 1));
        panel.setLayer(sprite, JLayeredPane.MODAL_LAYER);

        return botao;
    }

    private void setTurno(Jogador turno){
        this.turno = turno;

        if(turno == jogador) {
            turnoInicial = jogadorInicial;
            enableButtons();
        }
        else if(turno == cpu) {
            turnoInicial = cpuInicial;
            disableButtons();
        }

        snapshot = new GameSnapshot(todasAsPedras, mesa);

        turno.onInicioDoTurno(this);
    }

    private void proximoTurno() {
        somatorioDaMesa = 0;
        for(Conjunto c : mesa)
            somatorioDaMesa += c.getPontos();

        if(turno == jogador)
            setTurno(cpu);
        else if (turno == cpu)
            setTurno(jogador);
    }

    private void enableButtons() {
        botaoPassarVez.setEnabled();
        botaoRummikub.setEnabled();
        botaoFinalizarJogada.setEnabled();
    }

    private void disableButtons() {
        botaoPassarVez.setDisabled();
        botaoFinalizarJogada.setDisabled();
        botaoRummikub.setDisabled();
    }

    private void inicializaMonteDeCompras(){
        final String[] tipos = new String[]{Pedra.TIPO_NUMERO_1, Pedra.TIPO_NUMERO_2, Pedra.TIPO_NUMERO_3, Pedra.TIPO_NUMERO_4, Pedra.TIPO_NUMERO_5, Pedra.TIPO_NUMERO_6, Pedra.TIPO_NUMERO_7, Pedra.TIPO_NUMERO_8, Pedra.TIPO_NUMERO_9, Pedra.TIPO_NUMERO_10, Pedra.TIPO_NUMERO_11, Pedra.TIPO_NUMERO_12, Pedra.TIPO_NUMERO_13, Pedra.TIPO_CORINGA};
        final String[] cores = new String[]{Pedra.COR_AZUL, Pedra.COR_VERDE, Pedra.COR_VERMELHO, Pedra.COR_ROSA};

        //adiciona duas de cada peça númerica ao monte de compras
        for(int i = 0; i < cores.length * 2; i++) {
            for (int j = 0; j < tipos.length - 1; j++) {
                final Pedra pedra = novaPedra(new Pedra(tipos[j], cores[i % cores.length]));
                pedra.moveTo(grid, 0, 0);

                todasAsPedras.add(pedra);
                monteDeCompras.push(pedra);
            }
        }

        //adiciona os dois coringas ao monte de compras
        for(int i = 0; i < 2; i++) {
            final Pedra pedra = novaPedra(new Pedra(Pedra.TIPO_CORINGA));
            pedra.moveTo(grid, 0, 0);

            todasAsPedras.add(pedra);
            monteDeCompras.push(pedra);
        }

        //Embaralha as peças
        Collections.shuffle(monteDeCompras);
    }

    private void inicializaPartida(){
        somatorioDaMesa = 0;

        //cada jogador compra 14 peças
        for(int i = 0; i < 14; i++) {
            jogador.comprarPedra(monteDeCompras.pop(), grid);
            cpu.comprarPedra(monteDeCompras.pop(), grid);
        }

        //um jogador é escolhido aleatoriamente para começar
        if(Utils.randomRange(0, 1) == 0)
            setTurno(jogador);
        else
            setTurno(cpu);
    }

    /**
     * deve ser chamada após a validação de final de partida
     *
     */
    private void finalizaPartida() {
        // vitoria alternativa || vitoria padrão
        if (jogador == getVencedorVitoriaAlternativa(jogador, cpu) || jogador.getPedras().size() == 0) {

            jogador.addPontuacaoPartida(getPontuacaoVencedor(jogador, cpu));
            cpu.addPontuacaoPartida(getPontuacaoPerdedor(jogador, cpu));

        } else if (cpu == getVencedorVitoriaAlternativa(jogador, cpu) || cpu.getPedras().size() == 0){

            cpu.addPontuacaoPartida(getPontuacaoVencedor(cpu, jogador));
            jogador.addPontuacaoPartida(getPontuacaoPerdedor(cpu, jogador));

        }

    }

    /** contagem de pontos dos conjuntos da jogada **/
    private int pontuaJogada(){
        int somatorioAtual = 0;
        for(Conjunto c : mesa)
            somatorioAtual += c.getPontos();

        return somatorioAtual - somatorioDaMesa;
    }

    /** determina se a mesa atual é válida ou não **/
    private boolean validaMesa(){
        for(Conjunto c : mesa)
            if(!c.isSequencia() && !c.isGrupo())
                return false;
        return true;
    }

    /**
     * deve ser chamado no final de cada jogada validada
     * seta todos os conjuntos da mesa como não novo
     */
    @Deprecated //para que que isso serve?
    private void afterJogada() {
        for (Conjunto conjunto : mesa)
            conjunto.setOld();
    }

    public Pedra novaPedra(Pedra pedra){
        final JLabel sprite = pedra.onCreate(grid, frameWrapper, paneWrapper, this, this);

        //Adiciona o sprite ao frame para que seja renderizado na tela
        panel.add(sprite, new Integer(panel.getComponentCount() + 1));

        return pedra;
    }

    //documentação na interface CollisionChecker
    @Override
    public Pedra checkCollision(Pedra me, int offsetX) {
        final Point a = me.getLocation();
        a.x += offsetX;

        int size = grid.getCellSizeInPx();

        for(Pedra c : todasAsPedras){
            if(c.equals(me))
                continue;

            final Point b = c.getLocation();

            if(a.x < b.x + size && a.x + size > b.x && a.y < b.y + size && a.y + size > b.y)
                return c;
        }

        return null;
    }

    /**
     * calcula a pontuação do vencedor
     * score do vencedor é o módulo positivo do somatório das pontuações dos perdedores
     * @param vencedor player que apertou o botão "rummikub"
     * @param adversario único jogador adversário
     * @return coordenada do grid
     */
    public int getPontuacaoVencedor(Jogador vencedor, Jogador adversario) {
        List<Jogador> adversarios = new ArrayList<Jogador>();
        adversarios.add(adversario);
        return getPontuacaoVencedor(vencedor, adversarios);
    }

    /**
     * calcula a pontuação do vencedor
     * score do vencedor é o módulo positivo do somatório das pontuações dos perdedores
     * @param vencedor player que apertou o botão "rummikub"
     * @param adversarios lista de jogadores excluindo o vencedor
     * @return coordenada do grid
     */
    public int getPontuacaoVencedor(Jogador vencedor, List<Jogador> adversarios) {
        int score = 0;
        for (Jogador adversario : adversarios)
            score += -1 * getPontuacaoPerdedor(vencedor, adversario);

        return score;
    }

    /**
     * calcula a pontuação de um dos perdedores
     * score do perdedor é o módulo negativo do somatorio de sua mão descontado do somatorio da mão do vencedor
     * engloba a vitória padrão e vitória alternativa pois o somatório do vencedor é 0 na vitória padrão
     * @param vencedor player que apertou o botão "rummikub"/
     * @param perdedor lista de jogadores excluindo o vencedor
     * @return coordenada do grid
     */
    public int getPontuacaoPerdedor(Jogador vencedor, Jogador perdedor) {
        int score;
        score = perdedor.getSomatorioMao() - vencedor.getSomatorioMao();

        return -1 * score;
    }

    public Jogador getVencedorVitoriaAlternativa(List<Jogador> jogadores) {
        Jogador vencedor = jogadores.get(0);

        for (Jogador jogador : jogadores)
            if (jogador.getSomatorioMao() < vencedor.getSomatorioMao())
                vencedor = jogador;
        return vencedor;
    }

    public Jogador getVencedorVitoriaAlternativa(Jogador cpu, Jogador pessoa) {
        List<Jogador> jogadores = new ArrayList<>();
        jogadores.add(cpu);
        jogadores.add(pessoa);
        return getVencedorVitoriaAlternativa(jogadores);
    }

    /**
     * apura quem foi o vencedor da serie de partidas
     * @param jogadores lista de jogadores do jogo
     * @return Jogador vencedor do JOGO
     */
    public Jogador getVencedorJogo(List<Jogador> jogadores) {
        Jogador vencedor = null;

        for (Jogador jogador : jogadores) {
            if (vencedor == null || jogador.getSomaPontuacaoPartida() > vencedor.getSomaPontuacaoPartida())
                vencedor = jogador;
        }

        return vencedor;
    }

    /** Botão de Passar a Vez foi apertado, efetua a compra de pedra para o jogador atual e passa a vez **/
    @Override
    public void passarAVezButtonPressed() {
        if (monteDeCompras.empty())
            finalizaPartida();
        else {
            snapshot.restore(grid, this);
            turno.comprarPedra(monteDeCompras.pop(), grid);
            proximoTurno();
        }
    }

    /** Botão de Rummikub foi apertado, verifica se pode **/
    @Override
    public void rummikubButtonPressed() {
        if (turno.getPedras().isEmpty())
            finalizaPartida();
    }

    /** Botão de Finalizar Jogada foi apertado, verifica se pode **/
    @Override
    public void finalizarJogadaButtonPressed() {
        if(!validaMesa()) { // a mesa precisa ser válida
            //provavelmente uma mensagem na tela seria mais sutil, mas como não temos
            snapshot.restore(grid, this);
            return;
        }
        
        if(turnoInicial && pontuaJogada() < 30) // jogada inicial tem que fazer pelo menos 30 pontos
            return;

        if(pontuaJogada() <= 0) // jogador deve descer pelo menos uma peça na mesa para valer a jogada
            return;

        if(turno == jogador)
            jogadorInicial = false;
        else if(turno == cpu)
            cpuInicial = false;

        proximoTurno();
    }

    @Override
    public List<Conjunto> getConjuntos() {
        return new ArrayList<>(this.mesa);
    }

    @Override
    public void addConjunto(Conjunto c) {
        this.mesa.add(c);
    }

    @Override
    public void removeConjunto(Conjunto c) {
        this.mesa.remove(c);
    }
}