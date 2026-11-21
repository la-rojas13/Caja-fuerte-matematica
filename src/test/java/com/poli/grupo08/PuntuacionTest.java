package com.poli.grupo08;

import com.poli.grupo08.modelo.CajaFuerte;
import com.poli.grupo08.modelo.Dificultad;
import com.poli.grupo08.modelo.Division;
import com.poli.grupo08.modelo.Multiplicacion;
import com.poli.grupo08.modelo.Nivel;
import com.poli.grupo08.modelo.Puntuacion;
import com.poli.grupo08.modelo.Resta;
import com.poli.grupo08.modelo.Suma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Pruebas unitarias para {@link Puntuacion} validando la formula oficial:
 * 100 base + 20 * movimientosSobrantes - 10 * vecesDeshacer + 50 si se resuelve
 * en el minimo de movimientos de la dificultad.
 */
class PuntuacionTest {

    private Puntuacion puntuacion;

    @BeforeEach
    void setUp() {
        puntuacion = new Puntuacion();
    }

    @Test
    void calculaPuntajeConBonificacionPorMovimientosMinimosYSobrantes() {
        // Dificultad FACIL (minMovimientos = 2):
        // Resuelto en 2 movimientos, 2 sobrantes, 0 deshacer -> 100 + 20*2 - 0 + 50 = 190
        int ganados = puntuacion.registrarNivelCompletado(Dificultad.FACIL, 2, 2, 0);
        assertEquals(190, ganados);
        assertEquals(190, puntuacion.getPuntos());
        assertEquals(1, puntuacion.getAciertos());
    }

    @Test
    void aplicaPenalizacionPorDeshacerYSinBonoSiSuperaMinimoDeMovimientos() {
        // Dificultad MEDIO (minMovimientos = 3):
        // Resuelto en 4 movimientos (> 3, sin bono de 50), 1 sobrante, 2 deshacer:
        // 100 + (1 * 20) - (2 * 10) + 0 = 100
        int ganados = puntuacion.registrarNivelCompletado(Dificultad.MEDIO, 4, 1, 2);
        assertEquals(100, ganados);
        assertEquals(100, puntuacion.getPuntos());
    }

    @Test
    void calculaPuntajeDirectamenteDesdeCajaFuerteAbierta() {
        Nivel nivel = new Nivel(
                1,
                Dificultad.FACIL,
                5,
                16,
                4,
                List.of(new Suma(7), new Multiplicacion(3), new Resta(4), new Division(2))
        );
        CajaFuerte caja = new CajaFuerte(nivel);
        caja.aplicarOperacion(0); // 5 + 7 = 12
        caja.aplicarOperacion(3); // 12 / 2 = 6
        caja.deshacer();          // regresa a 12 (vecesDeshacer = 1, movimientosUsados = 1)
        caja.aplicarOperacion(2); // 12 - 4 = 8 (movimientosUsados = 2)
        caja.aplicarOperacion(0); // 8 + 7 = 15 (movimientosUsados = 3)
        caja.deshacer();          // regresa a 8 (vecesDeshacer = 2, movimientosUsados = 2)
        // Reemplazamos para llegar a 16:
        Nivel nivelExacto = new Nivel(
                1,
                Dificultad.FACIL,
                5,
                15,
                4,
                List.of(new Suma(7), new Multiplicacion(3), new Resta(4), new Division(2))
        );
        CajaFuerte cajaAbierta = new CajaFuerte(nivelExacto);
        cajaAbierta.aplicarOperacion(1); // 5 * 3 = 15 -> abierta en 1 movimiento (<= min 2), 3 sobrantes, 0 deshacer
        // 100 + 3*20 - 0 + 50 = 210
        int puntos = puntuacion.registrarNivelCompletado(cajaAbierta);
        assertEquals(210, puntos);
    }

    @Test
    void validaExcepcionesYReinicioDeEstadisticas() {
        puntuacion.registrarFallo();
        assertEquals(1, puntuacion.getFallos());
        puntuacion.reiniciar();
        assertEquals(0, puntuacion.getFallos());
        assertEquals(0, puntuacion.getPuntos());

        assertThrows(IllegalArgumentException.class,
                () -> puntuacion.registrarNivelCompletado(null, 2, 1, 0));
        assertThrows(IllegalArgumentException.class,
                () -> puntuacion.registrarNivelCompletado(Dificultad.FACIL, -1, 1, 0));
    }
}