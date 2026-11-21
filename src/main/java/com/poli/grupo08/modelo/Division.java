package com.poli.grupo08.modelo;

/**
 * Representa la operacion aritmetica de division entera exacta sobre el valor del visor.
 *
 * @author Laura Rojas
 * @author Equipo 08
 * @version 1.0
 */
public class Division extends Operacion {

    /**
     * Construye una operacion de division con el divisor fijo indicado.
     *
     * @param valor divisor fijo de la operacion (debe ser distinto de cero).
     * @throws IllegalArgumentException si {@code valor} es igual a cero.
     */
    public Division(int valor) {
        super(valor);
        if (valor == 0) {
            throw new IllegalArgumentException("El divisor no puede ser cero");
        }
    }

    /**
     * Valida la regla 5: el boton de division se desactiva cuando el resultado no seria exacto.
     *
     * @param numero numero actual en el visor.
     * @return {@code true} si {@code numero % valor == 0}; {@code false} en caso contrario.
     */
    @Override
    public boolean esAplicable(int numero) {
        return numero % valor == 0;
    }

    /**
     * Aplica la division exacta al numero actual del visor.
     *
     * @param numero numero actual en el visor.
     * @return cociente entero de {@code numero / valor}.
     * @throws IllegalStateException si la division no es exacta.
     */
    @Override
    public int aplicar(int numero) {
        validarOperacion(numero);
        return numero / valor;
    }
}