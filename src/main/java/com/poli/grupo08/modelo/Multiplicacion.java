package com.poli.grupo08.modelo;

public class Multiplicacion extends Operacion {
    public Multiplicacion(int valor) {
        super(valor);
    }

    //Multiplicar dos numeros positivos siempre da un positivo
    @Override
    public boolean esAplicable(int numero) {
        return true;
    }

    @Override
    public int aplicar(int numero) {
        validarOperacion(numero);
        return numero * valor;
    }
}
