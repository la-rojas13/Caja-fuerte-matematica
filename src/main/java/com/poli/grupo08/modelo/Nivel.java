package com.poli.grupo08.modelo;

import java.util.List;

/**
 * Representa la configuracion inmutable de un nivel del juego
 * La Caja Fuerte Matematica.
 *
 * @author Laura Rojas
 * @author Equipo 08
 * @version 1.0
 */
public class Nivel {

    /** Regla 1: cantidad de botones de operacion por nivel. */
    public static final int CANTIDAD_OPERACIONES = 4;

    /** Numero identificador del nivel (por ejemplo, Nivel 1). */
    private final int numero;

    /** Dificultad configurada para este nivel. */
    private final Dificultad dificultad;

    /** Numero con el que empieza la partida en el visor (por ejemplo, 5). */
    private final int numeroInicial;

    /** Numero que abre la caja fuerte (por ejemplo, 42). */
    private final int numeroObjetivo;

    /** Movimientos permitidos por nivel. */
    private final int maxMovimientos;

    /** Lista inmutable con las 4 operaciones del nivel. */
    private final List<Operacion> operaciones;

    /**
     * Construye un nuevo nivel validando sus parametros de acuerdo con las reglas del juego.
     *
     * @param numero identificador del nivel.
     * @param dificultad dificultad asignada al nivel.
     * @param numeroInicial valor inicial en el visor (no negativo).
     * @param numeroObjetivo valor objetivo para abrir la caja fuerte (no negativo).
     * @param maxMovimientos cantidad maxima de movimientos permitidos (mayor que cero).
     * @param operaciones lista con exactamente 4 operaciones.
     * @throws IllegalArgumentException si algun parametro es invalido.
     */
    public Nivel(int numero, Dificultad dificultad, int numeroInicial, int numeroObjetivo, int maxMovimientos, List<Operacion> operaciones) {
        if (numeroInicial < 0 || numeroObjetivo < 0) {
            throw new IllegalArgumentException("Los numeros no pueden ser negativos");
        }
        if (operaciones == null || operaciones.size() != CANTIDAD_OPERACIONES) {
            throw new IllegalArgumentException("Un nivel necesita exactamente "
                    + CANTIDAD_OPERACIONES + " operaciones");
        }
        if (dificultad == null) {
            throw new IllegalArgumentException("La dificultad no puede ser nula");
        }
        if (maxMovimientos <= 0) {
            throw new IllegalArgumentException("El maximo de movimientos debe ser mayor que cero");
        }
        this.numero = numero;
        this.dificultad = dificultad;
        this.numeroInicial = numeroInicial;
        this.numeroObjetivo = numeroObjetivo;
        this.maxMovimientos = maxMovimientos;
        this.operaciones = List.copyOf(operaciones);
    }

    /**
     * Obtiene la lista inmutable de operaciones disponibles en el nivel.
     *
     * @return lista de 4 instancias de {@link Operacion}.
     */
    public List<Operacion> getOperaciones() {
        return operaciones;
    }

    /**
     * Obtiene el numero maximo de movimientos permitidos en el nivel.
     *
     * @return maximo de movimientos.
     */
    public int getMaxMovimientos() {
        return maxMovimientos;
    }

    /**
     * Obtiene el numero objetivo que abre la caja fuerte.
     *
     * @return numero objetivo.
     */
    public int getNumeroObjetivo() {
        return numeroObjetivo;
    }

    /**
     * Obtiene el numero inicial mostrado en el visor al iniciar el nivel.
     *
     * @return numero inicial.
     */
    public int getNumeroInicial() {
        return numeroInicial;
    }

    /**
     * Obtiene la dificultad del nivel.
     *
     * @return dificultad asignada.
     */
    public Dificultad getDificultad() {
        return dificultad;
    }

    /**
     * Obtiene el numero identificador del nivel.
     *
     * @return numero del nivel.
     */
    public int getNumero() {
        return numero;
    }
}