package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rummikub.Tile;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {
    private Tile tile;

    /*
     * Metódo chamado antes de cada teste
     */
    @BeforeEach
    void setUp() {
        tile = new Tile();
    }

    /*
     * Metódo chamado depois de cada teste
     */
    @AfterEach
    void tearDown() {
    }

    /*
     * Testa se o objeto tile existe
     */
    @Test
    void tileNotNull() {
        assertNotNull(tile);
    }
}