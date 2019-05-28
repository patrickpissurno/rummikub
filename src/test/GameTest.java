package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rummikub.*;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameTest {

    private Game game;

    /*
     * Metódo chamado antes de cada teste
     */
    @BeforeEach
    void setUp() {
        game = new Game(new JLayeredPane(), new FrameWrapper(null));
    }

    /*
     * Metódo chamado depois de cada teste
     */
    @AfterEach
    void tearDown() {
    }

    /*
     * Testa se o objeto game existe
     */
    @Test
    void gameNotNull() {
        assertNotNull(game);
    }

    @Test
    void pontuacaoJogadoresVitoriaPadrao() {
        Jogador vencedor = new JogadorPessoa();
        Jogador adversario = new JogadorPessoa();

        adversario.clearPedras();
        adversario.addPedra(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));

        vencedor.clearPedras();

        assertEquals(0, vencedor.getPedras().size());
        assertEquals(1, adversario.getPedras().size());

        assertEquals(0, vencedor.getSomatorioMao());
        assertEquals(1, adversario.getSomatorioMao());

        assertEquals(1, game.getPontuacaoVencedor(vencedor, adversario));
        assertEquals(-1, game.getPontuacaoPerdedor(vencedor, adversario));
    }

    @Test
    void pontuacaoJogadoresVitoriaPadraoCoringa() {
        Jogador vencedor = new JogadorPessoa();
        Jogador adversario = new JogadorPessoa();

        adversario.clearPedras();
        adversario.addPedra(new Pedra(Pedra.TIPO_CORINGA));

        vencedor.clearPedras();

        assertEquals(0, vencedor.getPedras().size());
        assertEquals(1, adversario.getPedras().size());

        assertEquals(0, vencedor.getSomatorioMao());
        assertEquals(30, adversario.getSomatorioMao());

        assertEquals(30, game.getPontuacaoVencedor(vencedor, adversario));
        assertEquals(-30, game.getPontuacaoPerdedor(vencedor, adversario));
    }

    @Test
    void pontuacaoJogadoresVitoriaAlternativaCoringa() {
        Jogador vencedor = new JogadorPessoa();
        Jogador adversario = new JogadorPessoa();

        adversario.clearPedras();
        adversario.addPedra(new Pedra(Pedra.TIPO_CORINGA));

        vencedor.clearPedras();
        vencedor.addPedra(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));

        assertEquals(1, vencedor.getPedras().size());
        assertEquals(1, adversario.getPedras().size());

        assertEquals(1, vencedor.getSomatorioMao());
        assertEquals(30, adversario.getSomatorioMao());

        assertEquals(29, game.getPontuacaoVencedor(vencedor, adversario));
        assertEquals(-29, game.getPontuacaoPerdedor(vencedor, adversario));
    }


}