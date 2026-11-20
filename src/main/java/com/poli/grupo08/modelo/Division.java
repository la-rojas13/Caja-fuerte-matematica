package com.poli.grupo08.modelo;

public class Division extends Operacion{
    public Division(int valor) {
        super(valor);
    }
    /**
     * Valida esta regla 5: El botón de división se desactiva cuando el resultado no sería exacto.
     */
    @Override
    public boolean esAplicable(int numero) {
        return numero % valor == 0;
    }

    @Override
    public int aplicar(int numero) {
        validarOperacion(numero);
        return numero / valor;
    }
}
