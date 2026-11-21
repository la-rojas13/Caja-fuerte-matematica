package com.poli.grupo08.modelo;

/**
 * Representa la operacion aritmetica de multiplicacion sobre el valor del visor.
 *
 * @author Laura Rojas
 * @author Equipo 08
 * @version 1.0
 */
public class Multiplicacion extends Operacion {

    /**
     * Construye una operacion de multiplicacion con el factor fijo indicado.
     *
     * @param valor factor por el cual se multiplicara el numero del visor.
     */
    public Multiplicacion(int valor) {
        super(valor);
    }

    /**
     * Indica si la multiplicacion es aplicable al numero del visor.
     * Multiplicar dos numeros positivos siempre da un positivo.
     *
     * @param numero numero actual en el visor.
     * @return {@code true} siempre.
     */
    @Override
    public boolean esAplicable(int numero) {
        return true;
    }

    /**
     * Aplica la multiplicacion al numero actual del visor.
     *
     * @param numero numero actual en el visor.
     * @return resultado de {@code numero * valor}.
     */
    @Override
    public int aplicar(int numero) {
        validarOperacion(numero);
        return numero * valor;
    }
}