package com.poli.grupo08.modelo;

/**
 * Operaciones que se aplican en la caja fuerte
 */
public abstract class Operacion {

    // Representa el numero del fijo del boton ejemplo +7
    protected final int valor;

    public Operacion(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    /**
     * Numero hace referencia al numero que esta actualemente en el visor
     */
    public abstract boolean esAplicable(int numero);

    /**
     * Realizar la operacion (+,-,*,/) segun aplique
     */
    public abstract int aplicar(int numero);

    /**
     * Este metodo se encarga de validar si la operacion es posible ej(2-4 no lo es)
     */
    protected void validarOperacion(int numero) {
        if (!esAplicable(numero)) {
            throw new IllegalStateException("La operación no es aplicable al número " + numero);
        }
    }
}
