package com.poli.grupo08;

import com.poli.grupo08.modelo.Dificultad;
import com.poli.grupo08.modelo.Division;
import com.poli.grupo08.modelo.Multiplicacion;
import com.poli.grupo08.modelo.Nivel;
import com.poli.grupo08.modelo.Operacion;
import com.poli.grupo08.modelo.Resta;
import com.poli.grupo08.modelo.Suma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Pruebas unitarias para {@link Nivel} y {@link Dificultad}.
 */
class NivelTest {

    private List<Operacion> cuatroOperaciones;

    @BeforeEach
    void setUp() {
        cuatroOperaciones = List.of(
                new Suma(7),
                new Multiplicacion(3),
                new Resta(4),
                new Division(2)
        );
    }

    @Test
    void creaNivelValidoConCuatroOperacionesEInmutabilidad() {
        Nivel nivel = new Nivel(1, Dificultad.MEDIO, 5, 42, 5, cuatroOperaciones);

        assertEquals(1, nivel.getNumero());
        assertEquals(Dificultad.MEDIO, nivel.getDificultad());
        assertEquals(5, nivel.getNumeroInicial());
        assertEquals(42, nivel.getNumeroObjetivo());
        assertEquals(5, nivel.getMaxMovimientos());
        assertEquals(Nivel.CANTIDAD_OPERACIONES, nivel.getOperaciones().size());

        assertThrows(UnsupportedOperationException.class,
                () -> nivel.getOperaciones().add(new Suma(1)));
    }

    @Test
    void lanzaExcepcionAnteParametrosInvalidosEnConstructor() {
        assertThrows(IllegalArgumentException.class,
                () -> new Nivel(1, Dificultad.FACIL, -1, 42, 4, cuatroOperaciones));
        assertThrows(IllegalArgumentException.class,
                () -> new Nivel(1, Dificultad.FACIL, 5, -10, 4, cuatroOperaciones));
        assertThrows(IllegalArgumentException.class,
                () -> new Nivel(1, null, 5, 42, 4, cuatroOperaciones));
        assertThrows(IllegalArgumentException.class,
                () -> new Nivel(1, Dificultad.FACIL, 5, 42, 0, cuatroOperaciones));
        assertThrows(IllegalArgumentException.class,
                () -> new Nivel(1, Dificultad.FACIL, 5, 42, 4, List.of(new Suma(7), new Resta(4))));
    }

    @Test
    void dificultadesConfiguranLimitesCorrectos() {
        assertEquals(30, Dificultad.FACIL.getMaxNumero());
        assertEquals(2, Dificultad.FACIL.getMinMovimientos());
        assertEquals(3, Dificultad.FACIL.getMaxMovimientos());
        assertEquals(2, Dificultad.FACIL.getMargen());

        assertEquals(100, Dificultad.MEDIO.getMaxNumero());
        assertEquals(3, Dificultad.MEDIO.getMinMovimientos());

        assertEquals(500, Dificultad.DIFICIL.getMaxNumero());
        assertEquals(4, Dificultad.DIFICIL.getMinMovimientos());
    }
}