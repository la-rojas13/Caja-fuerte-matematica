package com.poli.grupo08;

import com.poli.grupo08.modelo.Division;
import com.poli.grupo08.modelo.Multiplicacion;
import com.poli.grupo08.modelo.Operacion;
import com.poli.grupo08.modelo.Resta;
import com.poli.grupo08.modelo.Suma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas unitarias para la jerarquia de {@link Operacion}
 * ({@link Suma}, {@link Resta}, {@link Multiplicacion} y {@link Division}).
 */
class OperacionTest {

    private Operacion suma;
    private Operacion resta;
    private Operacion multiplicacion;
    private Operacion division;

    @BeforeEach
    void setUp() {
        suma = new Suma(7);
        resta = new Resta(4);
        multiplicacion = new Multiplicacion(3);
        division = new Division(2);
    }

    @Test
    void sumaSiempreEsAplicableYCalculaResultadoCorrecto() {
        assertTrue(suma.esAplicable(0));
        assertTrue(suma.esAplicable(5));
        assertEquals(12, suma.aplicar(5));
        assertEquals(7, suma.getValor());
    }

    @Test
    void restaImpideResultadosNegativosYLanzaExcepcion() {
        assertTrue(resta.esAplicable(4));
        assertTrue(resta.esAplicable(10));
        assertEquals(0, resta.aplicar(4));
        assertEquals(6, resta.aplicar(10));

        assertFalse(resta.esAplicable(2));
        assertThrows(IllegalStateException.class, () -> resta.aplicar(2));
    }

    @Test
    void multiplicacionSiempreEsAplicableYMultiplicaCorrectamente() {
        assertTrue(multiplicacion.esAplicable(5));
        assertEquals(15, multiplicacion.aplicar(5));
        assertEquals(0, multiplicacion.aplicar(0));
    }

    @Test
    void divisionValidaExactitudYRechazaDivisionInexactaYPorCero() {
        assertTrue(division.esAplicable(12));
        assertEquals(6, division.aplicar(12));

        assertFalse(division.esAplicable(15));
        assertThrows(IllegalStateException.class, () -> division.aplicar(15));
        assertThrows(IllegalArgumentException.class, () -> new Division(0));
    }
}