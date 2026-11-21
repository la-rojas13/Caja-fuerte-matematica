package com.poli.grupo08.modelo;

/**
 * Representa la operacion aritmetica de resta sobre el valor del visor,
 * garantizando que el resultado nunca sea negativo.
 *
 * @author Laura Rojas
 * @author Equipo 08
 * @version 1.0
 */
public class Resta extends Operacion {

    /**
     * Construye una operacion de resta con el sustraendo fijo indicado.
     *
     * @param valor cantidad que se restara al numero del visor.
     */
    public Resta(int valor) {
        super(valor);
    }

    /**
     * Valida la regla 6: el numero del visor no puede ser negativo.
     * Si una resta llevaria por debajo de cero, ese boton se desactiva.
     *
     * @param numero numero actual en el visor.
     * @return {@code true} si {@code numero - valor >= 0}; {@code false} en caso contrario.
     */
    @Override
    public boolean esAplicable(int numero) {
        return numero - valor >= 0;
    }

    /**
     * Aplica la resta al numero actual del visor.
     *
     * @param numero numero actual en el visor.
     * @return resultado de {@code numero - valor}.
     * @throws IllegalStateException si el resultado seria negativo.
     */
    @Override
    public int aplicar(int numero) {
        validarOperacion(numero);
        return numero - valor;
    }
}