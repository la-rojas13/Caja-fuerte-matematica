package com.poli.grupo08.modelo;

public class Suma extends Operacion {
    public Suma(int valor) {
        super(valor);
    }

    // La suma con numeros positivos siempre es valida
    @Override
    public boolean esAplicable(int numero) {
        return true;
    }

    @Override
    public int aplicar(int numero) {
        validarOperacion(numero);
        return numero + valor;
    }
}
