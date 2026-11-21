package com.poli.grupo08.modelo;

/**
 * Representa la operacion aritmetica de suma sobre el valor del visor.
 *
 * @author Laura Rojas
 * @author Equipo 08
 * @version 1.0
 */
public class Suma extends Operacion {

    /**
     * Construye una operacion de suma con el sumando fijo indicado.
     *
     * @param valor cantidad que se sumara al numero del visor.
     */
    public Suma(int valor) {
        super(valor);
    }

    /**
     * Indica si la suma es aplicable al numero del visor.
     * La suma con numeros positivos siempre es valida.
     *
     * @param numero numero actual en el visor.
     * @return {@code true} siempre.
     */
    @Override
    public boolean esAplicable(int numero) {
        return true;
    }

    /**
     * Aplica la suma al numero actual del visor.
     *
     * @param numero numero actual en el visor.
     * @return resultado de {@code numero + valor}.
     */
    @Override
    public int aplicar(int numero) {
        validarOperacion(numero);
        return numero + valor;
    }
}