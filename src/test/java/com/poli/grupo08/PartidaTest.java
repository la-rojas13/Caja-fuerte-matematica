package com.poli.grupo08;

import com.poli.grupo08.modelo.Dificultad;
import com.poli.grupo08.modelo.Division;
import com.poli.grupo08.modelo.Multiplicacion;
import com.poli.grupo08.modelo.Nivel;
import com.poli.grupo08.modelo.Partida;
import com.poli.grupo08.modelo.Resta;
import com.poli.grupo08.modelo.Suma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias para {@link Partida}: 3 vidas iniciales, perdida de vida
 * al agotar movimientos, avance entre niveles y estados de victoria y derrota.
 */
class PartidaTest {

    private Nivel nivel1;
    private Nivel nivel2;
    private Partida partida;

    @BeforeEach
    void setUp() {
        nivel1 = new Nivel(
                1,
                Dificultad.FACIL,
                5,
                15,
                2,
                List.of(new Suma(7), new Multiplicacion(3), new Resta(4), new Division(2))
        );
        nivel2 = new Nivel(
                2,
                Dificultad.MEDIO,
                5,
                36,
                2,
                List.of(new Suma(7), new Multiplicacion(3), new Resta(4), new Division(2))
        );
        partida = new Partida("Laura", List.of(nivel1, nivel2));
    }

    @Test
    void iniciaConTresVidasYValidaParametrosDeConstructor() {
        assertEquals("Laura", partida.getNombreJugador());
        assertEquals(Partida.VIDAS_INICIALES, partida.getVidas());
        assertEquals(3, partida.getVidas());
        assertFalse(partida.isTerminada());

        assertThrows(IllegalArgumentException.class, () -> new Partida("  ", List.of(nivel1)));
        assertThrows(IllegalArgumentException.class, () -> new Partida("Laura", List.of()));
    }

    @Test
    void pierdeUnaVidaAlAgotarMovimientosYTerminaEnDerrotaAlPerderLasTres() {
        // Agota los 2 movimientos del nivel 1 tres veces seguidas (5 + 7 = 12, 12 - 4 = 8 != 15)
        partida.ejecutarOperacion(0);
        partida.ejecutarOperacion(2);
        assertEquals(2, partida.getVidas());
        assertEquals(5, partida.getCajaFuerte().getNumeroActual());
        assertFalse(partida.isTerminada());

        partida.ejecutarOperacion(0);
        partida.ejecutarOperacion(2);
        assertEquals(1, partida.getVidas());

        partida.ejecutarOperacion(0);
        partida.ejecutarOperacion(2);
        assertEquals(0, partida.getVidas());
        assertTrue(partida.isTerminada());
        assertTrue(partida.isPerdida());
        assertFalse(partida.isGanada());
        assertEquals(3, partida.getPuntuacion().getFallos());

        assertThrows(IllegalStateException.class, () -> partida.ejecutarOperacion(0));
    }

    @Test
    void avanzaEntreNivelesYGanaLaPartidaAcumulandoPuntaje() {
        // Nivel 1 (objetivo 15): 5 * 3 = 15 en 1 movimiento (1 sobrante, <= minMovimientos 2)
        // Puntos Nivel 1 = 100 + 20*1 - 0 + 50 = 170
        assertTrue(partida.ejecutarOperacion(1));
        assertEquals(170, partida.getPuntuacion().getPuntos());

        assertTrue(partida.avanzarNivel());
        assertEquals(1, partida.getIndiceNivelActual());

        // Nivel 2 (objetivo 36): 5 + 7 = 12, 12 * 3 = 36 en 2 movimientos (0 sobrantes, <= minMovimientos 3)
        // Puntos Nivel 2 = 100 + 0 - 0 + 50 = 150
        assertFalse(partida.ejecutarOperacion(0));
        assertTrue(partida.ejecutarOperacion(1));

        assertFalse(partida.avanzarNivel());
        assertTrue(partida.isTerminada());
        assertTrue(partida.isGanada());
        assertFalse(partida.isPerdida());
        assertEquals(320, partida.getPuntuacion().getPuntos());
    }

    @Test
    void impideAvanzarSinAbrirCajaYPermiteDeshacerYReiniciar() {
        assertThrows(IllegalStateException.class, () -> partida.avanzarNivel());

        partida.ejecutarOperacion(0); // 5 + 7 = 12
        assertTrue(partida.deshacerMovimiento());
        assertEquals(5, partida.getCajaFuerte().getNumeroActual());

        partida.ejecutarOperacion(0);
        partida.reiniciarNivel();
        assertEquals(0, partida.getCajaFuerte().getMovimientosUsados());
        assertEquals(3, partida.getVidas());
    }
}