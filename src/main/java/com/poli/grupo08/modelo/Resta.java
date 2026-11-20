package com.poli.grupo08.modelo;

public class Resta extends Operacion {

    public Resta(int valor) {
        super(valor);
    }
    /**
     * Valida esta regla 6: El número del visor no puede ser negativo. Si una resta llevaría por debajo de cero ese botón se desactiva
     */
    @Override
    public boolean esAplicable(int numero) {
        return numero - valor >= 0;
    }
    @Override
    public int aplicar(int numero) {
        validarOperacion(numero);
        return numero - valor;
    }
}
