package com.poli.grupo08.modelo;

/**
 * Calcula y almacena el puntaje y las estadisticas del jugador siguiendo la
 * formula oficial aprobada en la Entrega 1 de La Caja Fuerte Matematica:
 * <ul>
 *   <li>100 puntos base por superar el nivel.</li>
 *   <li>+20 puntos por cada movimiento sobrante.</li>
 *   <li>-10 puntos por cada vez que se uso deshacer.</li>
 *   <li>+50 puntos de bonificacion si se resolvio en el numero minimo de movimientos de la dificultad.</li>
 * </ul>
 *
 * @author Equipo 08
 * @version 1.0
 */
public class Puntuacion {

    /** Puntos base otorgados al superar cualquier nivel. */
    public static final int PUNTOS_BASE_NIVEL = 100;

    /** Bonificacion por cada movimiento sobrante al abrir la caja fuerte. */
    public static final int BONO_MOVIMIENTO_SOBRANTE = 20;

    /** Penalizacion por cada vez que el jugador utilizo la accion deshacer. */
    public static final int PENALIZACION_DESHACER = 10;

    /** Bonificacion por resolver el nivel en el minimo de movimientos de su dificultad. */
    public static final int BONO_MOVIMIENTOS_MINIMOS = 50;

    /** Puntaje total acumulado por el jugador. */
    private int puntos;

    /** Cantidad de niveles superados exitosamente. */
    private int aciertos;

    /** Cantidad de intentos fallidos al agotar movimientos. */
    private int fallos;

    /**
     * Construye un gestor de puntuacion inicializado en cero.
     */
    public Puntuacion() {
        reiniciar();
    }

    /**
     * Calcula y registra el puntaje obtenido al superar un nivel segun la formula oficial.
     *
     * @param dificultad dificultad del nivel superado.
     * @param movimientosUsados cantidad de movimientos empleados para abrir la caja.
     * @param movimientosSobrantes cantidad de movimientos restantes sin usar.
     * @param vecesDeshacer cantidad de veces que se uso la opcion deshacer.
     * @return puntos obtenidos en el nivel (minimo 0).
     * @throws IllegalArgumentException si {@code dificultad} es nula o algun contador es negativo.
     */
    public int registrarNivelCompletado(Dificultad dificultad, int movimientosUsados, int movimientosSobrantes, int vecesDeshacer) {
        if (dificultad == null) {
            throw new IllegalArgumentException("La dificultad no puede ser nula");
        }
        if (movimientosUsados < 0 || movimientosSobrantes < 0 || vecesDeshacer < 0) {
            throw new IllegalArgumentException("Los contadores de movimientos no pueden ser negativos");
        }
        int ganados = PUNTOS_BASE_NIVEL
                + (movimientosSobrantes * BONO_MOVIMIENTO_SOBRANTE)
                - (vecesDeshacer * PENALIZACION_DESHACER);
        if (movimientosUsados <= dificultad.getMinMovimientos()) {
            ganados += BONO_MOVIMIENTOS_MINIMOS;
        }
        ganados = Math.max(0, ganados);
        this.puntos += ganados;
        this.aciertos++;
        return ganados;
    }

    /**
     * Calcula y registra el puntaje obtenido a partir del estado de una {@link CajaFuerte} abierta.
     *
     * @param cajaFuerte caja fuerte que acaba de ser abierta.
     * @return puntos obtenidos en el nivel.
     * @throws IllegalArgumentException si {@code cajaFuerte} es nula o no esta abierta.
     */
    public int registrarNivelCompletado(CajaFuerte cajaFuerte) {
        if (cajaFuerte == null || !cajaFuerte.isAbierta()) {
            throw new IllegalArgumentException("La caja fuerte debe estar abierta para registrar puntaje");
        }
        return registrarNivelCompletado(
                cajaFuerte.getNivel().getDificultad(),
                cajaFuerte.getMovimientosUsados(),
                cajaFuerte.getMovimientosRestantes(),
                cajaFuerte.getVecesDeshacer()
        );
    }

    /**
     * Registra un fallo cuando el jugador agota los movimientos de un nivel sin abrir la caja.
     */
    public void registrarFallo() {
        this.fallos++;
    }

    /**
     * Reinicia a cero los puntos, aciertos y fallos acumulados.
     */
    public void reiniciar() {
        this.puntos = 0;
        this.aciertos = 0;
        this.fallos = 0;
    }

    /**
     * Obtiene el puntaje total acumulado.
     *
     * @return puntos acumulados.
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * Obtiene la cantidad de niveles superados.
     *
     * @return total de aciertos.
     */
    public int getAciertos() {
        return aciertos;
    }

    /**
     * Obtiene la cantidad de intentos fallidos.
     *
     * @return total de fallos.
     */
    public int getFallos() {
        return fallos;
    }
}