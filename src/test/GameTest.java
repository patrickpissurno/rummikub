package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rummikub.Game;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameTest {

    private Game game;

    /*
     * Metódo chamado antes de cada teste
     */
    @BeforeEach
    void setUp() {
        game = new Game(new JLayeredPane());
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

}