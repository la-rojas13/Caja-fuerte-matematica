package com.poli.grupo08.modelo;

/**
 * Define los niveles de dificultad disponibles para una partida de
 * La Caja Fuerte Matematica, especificando limites de numeros y movimientos.
 *
 * @author Laura Rojas
 * @author Equipo 08
 * @version 1.0
 */
public enum Dificultad {

    /** Dificultad facil: numeros objetivo hasta 30, 2 a 3 movimientos y margen de 2. */
    FACIL(30, 2, 3, 2),

    /** Dificultad media: numeros objetivo hasta 100, 3 a 4 movimientos y margen de 1. */
    MEDIO(100, 3, 4, 1),

    /** Dificultad dificil: numeros objetivo hasta 500, 4 a 6 movimientos y sin margen extra. */
    DIFICIL(500, 4, 6, 0);

    /** Valor maximo permitido para el numero objetivo en esta dificultad. */
    private final int maxNumero;

    /** Cantidad minima de movimientos requeridos para la solucion optima. */
    private final int minMovimientos;

    /** Cantidad maxima de movimientos base permitidos en esta dificultad. */
    private final int maxMovimientos;

    /** Margen adicional de movimientos permitido sobre la solucion optima. */
    private final int margen;

    /**
     * Construye una constante de dificultad con sus parametros de configuracion.
     *
     * @param maxNumero valor maximo del numero objetivo.
     * @param minMovimientos cantidad minima de movimientos esperados.
     * @param maxMovimientos cantidad maxima de movimientos base.
     * @param margen movimientos adicionales de margen permitidos.
     */
    Dificultad(int maxNumero, int minMovimientos, int maxMovimientos, int margen) {
        this.maxNumero = maxNumero;
        this.minMovimientos = minMovimientos;
        this.maxMovimientos = maxMovimientos;
        this.margen = margen;
    }

    /**
     * Obtiene el margen adicional de movimientos permitido.
     *
     * @return cantidad de movimientos de margen.
     */
    public int getMargen() {
        return margen;
    }

    /**
     * Obtiene la cantidad maxima de movimientos base de la dificultad.
     *
     * @return maximo de movimientos permitidos.
     */
    public int getMaxMovimientos() {
        return maxMovimientos;
    }

    /**
     * Obtiene la cantidad minima de movimientos esperados para la bonificacion optima.
     *
     * @return minimo de movimientos de la dificultad.
     */
    public int getMinMovimientos() {
        return minMovimientos;
    }

    /**
     * Obtiene el numero maximo permitido como objetivo en la dificultad.
     *
     * @return valor maximo del numero objetivo.
     */
    public int getMaxNumero() {
        return maxNumero;
    }
}