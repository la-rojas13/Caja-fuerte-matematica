package com.poli.grupo08.modelo;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Gestiona el estado interactivo de la caja fuerte matematica durante un nivel:
 * visor actual, movimientos realizados, pila de historial para deshacer,
 * contador de usos de deshacer, reinicio y estado de apertura.
 *
 * @author Equipo 08
 * @version 1.0
 */
public class CajaFuerte {

    /** Nivel asociado a la caja fuerte. */
    private final Nivel nivel;

    /** Valor actual que se muestra en el visor de la caja fuerte. */
    private int numeroActual;

    /** Cantidad de movimientos usados en el intento actual. */
    private int movimientosUsados;

    /** Cantidad de veces que el jugador ha utilizado la accion de deshacer en el nivel. */
    private int vecesDeshacer;

    /** Pila de estados anteriores del visor para permitir deshacer movimientos. */
    private final Deque<Integer> historialEstados;

    /** Indica si la caja fuerte ya fue abierta al alcanzar el numero objetivo. */
    private boolean abierta;

    /**
     * Construye una caja fuerte asociada al nivel especificado.
     *
     * @param nivel configuracion del nivel a jugar.
     * @throws IllegalArgumentException si {@code nivel} es nulo.
     */
    public CajaFuerte(Nivel nivel) {
        if (nivel == null) {
            throw new IllegalArgumentException("El nivel no puede ser nulo");
        }
        this.nivel = nivel;
        this.historialEstados = new ArrayDeque<>();
        this.vecesDeshacer = 0;
        reiniciar();
    }

    /**
     * Aplica la operacion ubicada en el indice indicado (0 a 3) sobre el numero actual del visor.
     *
     * @param indice indice del boton de operacion dentro del nivel.
     * @return {@code true} si tras el movimiento se alcanzo el numero objetivo y la caja se abrio; {@code false} en caso contrario.
     * @throws IndexOutOfBoundsException si el indice esta fuera del rango de operaciones (0..3).
     * @throws IllegalStateException si la caja ya esta abierta, no quedan movimientos o la operacion no es aplicable.
     */
    public boolean aplicarOperacion(int indice) {
        if (abierta) {
            throw new IllegalStateException("La caja fuerte ya esta abierta");
        }
        if (movimientosUsados >= nivel.getMaxMovimientos()) {
            throw new IllegalStateException("No quedan movimientos disponibles");
        }
        Operacion operacion = nivel.getOperaciones().get(indice);
        int nuevoValor = operacion.aplicar(numeroActual);
        historialEstados.push(numeroActual);
        numeroActual = nuevoValor;
        movimientosUsados++;
        abierta = (numeroActual == nivel.getNumeroObjetivo());
        return abierta;
    }

    /**
     * Revierte el ultimo movimiento realizado, restaurando el estado previo de la pila,
     * descontando un movimiento usado y registrando el uso de deshacer.
     *
     * @return {@code true} si se deshizo el movimiento; {@code false} si el historial esta vacio o la caja ya esta abierta.
     */
    public boolean deshacer() {
        if (abierta || historialEstados.isEmpty()) {
            return false;
        }
        numeroActual = historialEstados.pop();
        movimientosUsados--;
        vecesDeshacer++;
        abierta = (numeroActual == nivel.getNumeroObjetivo());
        return true;
    }

    /**
     * Reinicia el visor al numero inicial del nivel, limpia el historial de estados
     * y restablece los movimientos usados a cero.
     */
    public void reiniciar() {
        this.numeroActual = nivel.getNumeroInicial();
        this.movimientosUsados = 0;
        this.historialEstados.clear();
        this.abierta = (this.numeroActual == nivel.getNumeroObjetivo());
    }

    /**
     * Indica si se agotaron todos los movimientos permitidos sin haber abierto la caja.
     *
     * @return {@code true} si no quedan movimientos y la caja sigue cerrada.
     */
    public boolean estaSinMovimientos() {
        return !abierta && movimientosUsados >= nivel.getMaxMovimientos();
    }

    /**
     * Calcula cuantos movimientos quedan disponibles en el nivel actual.
     *
     * @return cantidad de movimientos sobrantes.
     */
    public int getMovimientosRestantes() {
        return Math.max(0, nivel.getMaxMovimientos() - movimientosUsados);
    }

    /**
     * Obtiene el nivel configurado en la caja fuerte.
     *
     * @return instancia de {@link Nivel}.
     */
    public Nivel getNivel() {
        return nivel;
    }

    /**
     * Obtiene el numero actual en el visor.
     *
     * @return valor actual del visor.
     */
    public int getNumeroActual() {
        return numeroActual;
    }

    /**
     * Obtiene la cantidad de movimientos usados actualmente.
     *
     * @return movimientos realizados.
     */
    public int getMovimientosUsados() {
        return movimientosUsados;
    }

    /**
     * Obtiene la cantidad de veces que se ha utilizado la funcion deshacer en este nivel.
     *
     * @return numero de usos de deshacer.
     */
    public int getVecesDeshacer() {
        return vecesDeshacer;
    }

    /**
     * Indica si la caja fuerte ya fue abierta.
     *
     * @return {@code true} si esta abierta; {@code false} si sigue cerrada.
     */
    public boolean isAbierta() {
        return abierta;
    }

    /**
     * Obtiene una vista inmutable de los estados almacenados en el historial.
     *
     * @return lista inmutable con el historial de valores del visor.
     */
    public List<Integer> getHistorialEstados() {
        return List.copyOf(historialEstados);
    }
}