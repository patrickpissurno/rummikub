package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rummikub.Conjunto;
import rummikub.Pedra;


import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ConjuntoTest {


    @Test
    void grupoDe4Cores() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_ROSA));
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_VERDE));
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_VERMELHO));

        Conjunto conjunto = new Conjunto(pedras);

        assertTrue(conjunto.isGrupo());
    }

    @Test
    void grupoDe3Cores() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_ROSA));
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_VERDE));

        Conjunto conjunto = new Conjunto(pedras);

        assertTrue(conjunto.isGrupo());
    }

    @Test
    void grupoDe4CoresComCoringa() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new Pedra(Pedra.TIPO_CORINGA, Pedra.COR_ROSA));
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_VERDE));
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_VERMELHO));

        Conjunto conjunto = new Conjunto(pedras);

        assertTrue(conjunto.isGrupo());
    }

    @Test
    void grupoDe3CoresComCoringa() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new Pedra(Pedra.TIPO_CORINGA, Pedra.COR_ROSA));
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_VERDE));

        Conjunto conjunto = new Conjunto(pedras);

        assertTrue(conjunto.isGrupo());
    }

    @Test
    void grupoDe4CoresRepetidas() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_ROSA));
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_VERDE));
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_VERDE));

        Conjunto conjunto = new Conjunto(pedras);

        assertFalse(conjunto.isGrupo());
    }

    @Test
    void grupoDe3CoresRepetidas() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_ROSA));
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));

        Conjunto conjunto = new Conjunto(pedras);

        assertFalse(conjunto.isGrupo());
    }

    @Test
    void grupoDe4CoresRepetidasComCoringa() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new Pedra(Pedra.TIPO_CORINGA, Pedra.COR_ROSA));
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_ROSA));
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_VERMELHO));

        Conjunto conjunto = new Conjunto(pedras);

        assertFalse(conjunto.isGrupo());
    }

    @Test
    void grupoDe3CoresRepetidaComCoringa() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new Pedra(Pedra.TIPO_CORINGA, Pedra.COR_AZUL));
        pedras.add(new Pedra(Pedra.TIPO_NUMERO_1, Pedra.COR_VERDE));

        Conjunto conjunto = new Conjunto(pedras);

        assertFalse(conjunto.isGrupo());
    }

}