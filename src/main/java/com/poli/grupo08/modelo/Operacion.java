package com.poli.grupo08.modelo;

/**
 * Clase base abstracta que representa una operacion matematica aplicable
 * al numero actual mostrado en el visor de la caja fuerte.
 *
 * @author Laura Rojas
 * @author Equipo 08
 * @version 1.0
 */
public abstract class Operacion {

    /** Representa el numero fijo del boton de operacion (por ejemplo, +7). */
    protected final int valor;

    /**
     * Construye una operacion con un valor fijo.
     *
     * @param valor operando fijo asociado al boton de operacion.
     */
    public Operacion(int valor) {
        this.valor = valor;
    }

    /**
     * Obtiene el valor numerico fijo asociado a esta operacion.
     *
     * @return valor fijo de la operacion.
     */
    public int getValor() {
        return valor;
    }

    /**
     * Evalua si la operacion es aplicable al numero que esta actualmente en el visor.
     *
     * @param numero numero actual en el visor de la caja fuerte.
     * @return {@code true} si la operacion se puede aplicar; {@code false} en caso contrario.
     */
    public abstract boolean esAplicable(int numero);

    /**
     * Realiza la operacion matematica (+, -, *, /) sobre el numero recibido.
     *
     * @param numero numero actual en el visor de la caja fuerte.
     * @return resultado entero tras aplicar la operacion.
     * @throws IllegalStateException si la operacion no es aplicable al numero dado.
     */
    public abstract int aplicar(int numero);

    /**
     * Valida si la operacion es posible antes de ejecutarla (por ejemplo, 2 - 4 no lo es).
     *
     * @param numero numero actual en el visor de la caja fuerte.
     * @throws IllegalStateException si {@link #esAplicable(int)} retorna {@code false}.
     */
    protected void validarOperacion(int numero) {
        if (!esAplicable(numero)) {
            throw new IllegalStateException("La operacion no es aplicable al numero " + numero);
        }
    }
}