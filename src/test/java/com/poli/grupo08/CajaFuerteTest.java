package com.poli.grupo08;

import com.poli.grupo08.modelo.CajaFuerte;
import com.poli.grupo08.modelo.Dificultad;
import com.poli.grupo08.modelo.Division;
import com.poli.grupo08.modelo.Multiplicacion;
import com.poli.grupo08.modelo.Nivel;
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
 * Pruebas unitarias para {@link CajaFuerte}: caso oficial (5 -> 42 con +7, x3, -4, /2),
 * deshacer con pila de historial, reiniciar y agotamiento de movimientos.
 */
class CajaFuerteTest {

    private Nivel nivelOficial;
    private CajaFuerte cajaFuerte;

    @BeforeEach
    void setUp() {
        nivelOficial = new Nivel(
                1,
                Dificultad.MEDIO,
                5,
                42,
                5,
                List.of(
                        new Suma(7),           // indice 0: +7
                        new Multiplicacion(3), // indice 1: x3
                        new Resta(4),          // indice 2: -4
                        new Division(2)        // indice 3: /2
                )
        );
        cajaFuerte = new CajaFuerte(nivelOficial);
    }

    @Test
    void abreCajaFuerteEnCasoOficialDeCincoACuarentaYDos() {
        assertEquals(5, cajaFuerte.getNumeroActual());
        assertFalse(cajaFuerte.isAbierta());

        assertFalse(cajaFuerte.aplicarOperacion(1)); // 5 * 3 = 15
        assertEquals(15, cajaFuerte.getNumeroActual());

        assertFalse(cajaFuerte.aplicarOperacion(2)); // 15 - 4 = 11
        assertEquals(11, cajaFuerte.getNumeroActual());

        assertFalse(cajaFuerte.aplicarOperacion(0)); // 11 + 7 = 18
        assertEquals(18, cajaFuerte.getNumeroActual());

        assertFalse(cajaFuerte.aplicarOperacion(2)); // 18 - 4 = 14
        assertEquals(14, cajaFuerte.getNumeroActual());

        assertTrue(cajaFuerte.aplicarOperacion(1));  // 14 * 3 = 42 -> ABIERTA
        assertEquals(42, cajaFuerte.getNumeroActual());
        assertTrue(cajaFuerte.isAbierta());
        assertEquals(5, cajaFuerte.getMovimientosUsados());
        assertEquals(0, cajaFuerte.getMovimientosRestantes());
    }

    @Test
    void deshacerConPilaDeHistorialYReiniciarFuncionanCorrectamente() {
        assertFalse(cajaFuerte.deshacer());

        cajaFuerte.aplicarOperacion(0); // 5 + 7 = 12
        cajaFuerte.aplicarOperacion(3); // 12 / 2 = 6
        assertEquals(6, cajaFuerte.getNumeroActual());
        assertEquals(2, cajaFuerte.getMovimientosUsados());
        assertEquals(2, cajaFuerte.getHistorialEstados().size());

        assertTrue(cajaFuerte.deshacer());
        assertEquals(12, cajaFuerte.getNumeroActual());
        assertEquals(1, cajaFuerte.getMovimientosUsados());
        assertEquals(1, cajaFuerte.getVecesDeshacer());

        cajaFuerte.reiniciar();
        assertEquals(5, cajaFuerte.getNumeroActual());
        assertEquals(0, cajaFuerte.getMovimientosUsados());
        assertTrue(cajaFuerte.getHistorialEstados().isEmpty());
        assertFalse(cajaFuerte.isAbierta());
    }

    @Test
    void detectaAgotamientoDeMovimientosYLanzaExcepciones() {
        for (int i = 0; i < 5; i++) {
            assertFalse(cajaFuerte.aplicarOperacion(0)); // suma +7 cinco veces: 5 -> 40 != 42
        }
        assertEquals(40, cajaFuerte.getNumeroActual());
        assertTrue(cajaFuerte.estaSinMovimientos());
        assertEquals(0, cajaFuerte.getMovimientosRestantes());
        assertThrows(IllegalStateException.class, () -> cajaFuerte.aplicarOperacion(0));
    }

    @Test
    void constructorRechazaNivelNulo() {
        assertThrows(IllegalArgumentException.class, () -> new CajaFuerte(null));
    }
}