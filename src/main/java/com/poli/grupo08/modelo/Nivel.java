package com.poli.grupo08.modelo;

import java.util.List;

public class Nivel {
    //Regla 1 - Cantidad de botones de operación por nivel.
    public static final int CANTIDAD_OPERACIONES = 4;
    //Numero del nivel ej: Nivel 1
    private final int numero;
    private final Dificultad dificultad;
    //Numero con el que empiza la partida ejemplo 5
    private final int numeroInicial;
    //Numero que abre la caja fuerte ejemplo 42
    private final int numeroObjetivo;
    //movimientos permitidos por nivel
    private final int maxMovimientos;
    private final List<Operacion> operaciones;

    public Nivel(int numero, Dificultad dificultad, int numeroInicial, int numeroObjetivo, int maxMovimientos, List<Operacion> operaciones) {
        if (numeroInicial < 0 || numeroObjetivo < 0) {
            throw new IllegalArgumentException("Los números no pueden ser negativos");
        }
        if (operaciones == null || operaciones.size() != CANTIDAD_OPERACIONES) {
            throw new IllegalArgumentException("Un nivel necesita exactamente "
                    + CANTIDAD_OPERACIONES + " operaciones");
        }
        if (dificultad == null) {
            throw new IllegalArgumentException("La dificultad no puede ser nula");
        }
        if (maxMovimientos <= 0) {
            throw new IllegalArgumentException("El máximo de movimientos debe ser mayor que cero");
        }
        this.numero = numero;
        this.dificultad = dificultad;
        this.numeroInicial = numeroInicial;
        this.numeroObjetivo = numeroObjetivo;
        this.maxMovimientos = maxMovimientos;
        this.operaciones = List.copyOf(operaciones);
    }

    public List<Operacion> getOperaciones() {
        return operaciones;
    }

    public int getMaxMovimientos() {
        return maxMovimientos;
    }

    public int getNumeroObjetivo() {
        return numeroObjetivo;
    }

    public int getNumeroInicial() {
        return numeroInicial;
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public int getNumero() {
        return numero;
    }
}
