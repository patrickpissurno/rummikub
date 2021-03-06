package test;

import org.junit.jupiter.api.Test;
import rummikub.Conjunto;
import rummikub.ConjuntoVirtual;
import rummikub.Pedra;
import rummikub.PedraVirtual;


import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ConjuntoTest {


    @Test
    void grupoDe4Cores() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_ROSA));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_VERDE));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_VERMELHO));

        ConjuntoVirtual conjunto = new ConjuntoVirtual(pedras);

        assertTrue(conjunto.isGrupo());
    }

    @Test
    void grupoDe3Cores() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_ROSA));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_VERDE));

        ConjuntoVirtual conjunto = new ConjuntoVirtual(pedras);

        assertTrue(conjunto.isGrupo());
    }

    @Test
    void grupoDe4CoresComCoringa() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_CORINGA, Pedra.COR_ROSA));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_VERDE));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_VERMELHO));

        ConjuntoVirtual conjunto = new ConjuntoVirtual(pedras);

        assertTrue(conjunto.isGrupo());
    }

    @Test
    void grupoDe3CoresComCoringa() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_CORINGA, Pedra.COR_ROSA));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_VERDE));

        ConjuntoVirtual conjunto = new ConjuntoVirtual(pedras);

        assertTrue(conjunto.isGrupo());
    }

    @Test
    void grupoDe4CoresRepetidas() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_ROSA));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_VERDE));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_VERDE));

        ConjuntoVirtual conjunto = new ConjuntoVirtual(pedras);

        assertFalse(conjunto.isGrupo());
    }

    @Test
    void grupoDe3CoresRepetidas() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_ROSA));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));

        ConjuntoVirtual conjunto = new ConjuntoVirtual(pedras);

        assertFalse(conjunto.isGrupo());
    }

    @Test
    void grupoDe4CoresRepetidasComCoringa() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_CORINGA, Pedra.COR_ROSA));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_ROSA));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_VERMELHO));

        ConjuntoVirtual conjunto = new ConjuntoVirtual(pedras);

        assertFalse(conjunto.isGrupo());
    }

    @Test
    void grupoDe3CoresRepetidaComCoringa() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_CORINGA, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_VERDE));

        ConjuntoVirtual conjunto = new ConjuntoVirtual(pedras);

        assertFalse(conjunto.isGrupo());
    }

    @Test
    void sequenciaDe3Pedras() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_2, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_3, Pedra.COR_AZUL));

        ConjuntoVirtual conjunto = new ConjuntoVirtual(pedras);

        assertTrue(conjunto.isSequencia());
    }

    @Test
    void sequenciaDe3PedrasDesordenado() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_3, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_2, Pedra.COR_AZUL));

        ConjuntoVirtual conjunto = new ConjuntoVirtual(pedras);

        assertTrue(conjunto.isSequencia());
    }

    @Test
    void sequenciaDe3PedrasComCoringaNoMeio() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_CORINGA, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_3, Pedra.COR_AZUL));

        ConjuntoVirtual conjunto = new ConjuntoVirtual(pedras);

        assertTrue(conjunto.isSequencia());
    }

    @Test
    void sequenciaDe3PedrasComCoringaNaBorda() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_2, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_CORINGA, Pedra.COR_AZUL));

        ConjuntoVirtual conjunto = new ConjuntoVirtual(pedras);

        assertTrue(conjunto.isSequencia());
    }


    @Test
    void sequenciaDe2Pedras() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_2, Pedra.COR_AZUL));

        ConjuntoVirtual conjunto = new ConjuntoVirtual(pedras);

        assertFalse(conjunto.isSequencia());
    }

    @Test
    void sequenciaDe2PedrasComCoringa() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_CORINGA, Pedra.COR_AZUL));

        ConjuntoVirtual conjunto = new ConjuntoVirtual(pedras);

        assertFalse(conjunto.isSequencia());
    }

    @Test
    void sequenciaComPedraRepetida() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_2, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_3, Pedra.COR_AZUL));

        ConjuntoVirtual conjunto = new ConjuntoVirtual(pedras);

        assertFalse(conjunto.isSequencia());
    }

    @Test
    void sequenciaComPedraRepetidaECoringa() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_2, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_3, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_CORINGA));

        ConjuntoVirtual conjunto = new ConjuntoVirtual(pedras);

        assertFalse(conjunto.isSequencia());
    }

    @Test
    void split5Pedras() {
        LinkedList<Pedra> pedras = new LinkedList<>();
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras.add(new PedraVirtual(Pedra.TIPO_CORINGA, Pedra.COR_ROSA));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_VERDE));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_VERMELHO));
        pedras.add(new PedraVirtual(Pedra.TIPO_NUMERO_2, Pedra.COR_VERMELHO));

        ConjuntoVirtual conjunto = new ConjuntoVirtual(pedras);

        ArrayList<Conjunto> conjuntos = conjunto.split(pedras.get(2));

        assertEquals(conjuntos.size(), 2);
        assertEquals(conjuntos.get(0).size() + conjuntos.get(1).size(), pedras.size() - 1);
    }

    @Test
    void movePedraDaBorda() {
        LinkedList<Pedra> pedras1 = new LinkedList<>();
        pedras1.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras1.add(new PedraVirtual(Pedra.TIPO_CORINGA, Pedra.COR_ROSA));
        pedras1.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_VERDE));
        pedras1.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_VERMELHO));
        pedras1.add(new PedraVirtual(Pedra.TIPO_NUMERO_2, Pedra.COR_VERMELHO));

        LinkedList<Pedra> pedras2 = new LinkedList<>();
        pedras2.add(new PedraVirtual(Pedra.TIPO_NUMERO_1, Pedra.COR_AZUL));
        pedras2.add(new PedraVirtual(Pedra.TIPO_CORINGA, Pedra.COR_AZUL));

        ConjuntoVirtual conjunto1 = new ConjuntoVirtual(pedras1);

        ConjuntoVirtual conjunto2 = new ConjuntoVirtual(pedras2);

        conjunto1.movePedra(pedras1.getFirst(), conjunto2);
        assertEquals(conjunto1.size(), 4);
        assertEquals(conjunto2.size(), 3);
    }
}