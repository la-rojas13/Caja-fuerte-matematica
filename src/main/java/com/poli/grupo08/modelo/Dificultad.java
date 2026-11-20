package com.poli.grupo08.modelo;

public enum Dificultad {
    //maxNúmero(objetivo), minMovimientos, maxMovimientos, margen(Refiere al limite de movimientos ej, Solución + 2)

    FACIL(30, 2, 3, 2),
    MEDIO(100, 3, 4, 1),
    DIFICIL(500, 4, 6, 0);

    private final int maxNumero;
    private final int minMovimientos;
    private final int maxMovimientos;
    private final int margen;

    Dificultad(int maxNumero, int minMovimientos, int maxMovimientos, int margen) {
        this.maxNumero = maxNumero;
        this.minMovimientos = minMovimientos;
        this.maxMovimientos = maxMovimientos;
        this.margen = margen;
    }

    public int getMargen() {
        return margen;
    }

    public int getMaxMovimientos() {
        return maxMovimientos;
    }

    public int getMinMovimientos() {
        return minMovimientos;
    }

    public int getMaxNumero() {
        return maxNumero;
    }
}
