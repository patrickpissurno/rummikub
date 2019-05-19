package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rummikub.Conjunto;
import rummikub.Pedra;


import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

}