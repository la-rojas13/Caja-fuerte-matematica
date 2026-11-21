package com.poli.grupo08.modelo;

import java.util.List;

/**
 * Coordina el desarrollo completo de una partida de La Caja Fuerte Matematica:
 * datos del jugador, lista de niveles, sistema de 3 vidas iniciales,
 * avance de nivel, puntaje acumulado y estados de victoria o derrota.
 *
 * @author Equipo 08
 * @version 1.0
 */
public class Partida {

    /** Cantidad inicial de vidas al comenzar la partida. */
    public static final int VIDAS_INICIALES = 3;

    /** Nombre del jugador. */
    private final String nombreJugador;

    /** Lista inmutable de niveles que componen la partida. */
    private final List<Nivel> niveles;

    /** Indice base cero del nivel actual. */
    private int indiceNivelActual;

    /** Vidas restantes del jugador. */
    private int vidas;

    /** Caja fuerte activa correspondiente al nivel actual. */
    private CajaFuerte cajaFuerte;

    /** Gestor del puntaje acumulado en la partida. */
    private final Puntuacion puntuacion;

    /** Indica si la partida ya termino (por victoria o por quedarse sin vidas). */
    private boolean terminada;

    /**
     * Construye una nueva partida con 3 vidas iniciales para el jugador indicado.
     *
     * @param nombreJugador nombre del jugador (no nulo ni en blanco).
     * @param niveles lista ordenada de niveles (no nula ni vacia).
     * @throws IllegalArgumentException si el nombre o la lista de niveles son invalidos.
     */
    public Partida(String nombreJugador, List<Nivel> niveles) {
        if (nombreJugador == null || nombreJugador.isBlank()) {
            throw new IllegalArgumentException("El nombre del jugador no puede estar vacio");
        }
        if (niveles == null || niveles.isEmpty()) {
            throw new IllegalArgumentException("La partida requiere al menos un nivel");
        }
        this.nombreJugador = nombreJugador.trim();
        this.niveles = List.copyOf(niveles);
        this.indiceNivelActual = 0;
        this.vidas = VIDAS_INICIALES;
        this.puntuacion = new Puntuacion();
        this.cajaFuerte = new CajaFuerte(this.niveles.get(0));
        this.terminada = false;
    }

    /**
     * Ejecuta una operacion sobre la caja fuerte del nivel actual.
     * Si la caja se abre, calcula y suma el puntaje del nivel.
     * Si se agotan los movimientos sin abrirla, descuenta 1 vida; si aun quedan
     * vidas reinicia el nivel, o marca la partida como terminada si llega a 0 vidas.
     *
     * @param indiceOperacion indice de la operacion a aplicar (0 a 3).
     * @return {@code true} si la caja fuerte se abrio con este movimiento; {@code false} en caso contrario.
     * @throws IllegalStateException si la partida ya termino o la operacion no es aplicable.
     */
    public boolean ejecutarOperacion(int indiceOperacion) {
        validarPartidaActiva();
        boolean abierta = cajaFuerte.aplicarOperacion(indiceOperacion);
        if (abierta) {
            puntuacion.registrarNivelCompletado(cajaFuerte);
            return true;
        }
        if (cajaFuerte.estaSinMovimientos()) {
            vidas--;
            puntuacion.registrarFallo();
            if (vidas <= 0) {
                vidas = 0;
                terminada = true;
            } else {
                cajaFuerte.reiniciar();
            }
        }
        return false;
    }

    /**
     * Deshace el ultimo movimiento en la caja fuerte del nivel actual.
     *
     * @return {@code true} si se deshizo un movimiento; {@code false} si no habia movimientos previos.
     * @throws IllegalStateException si la partida ya termino.
     */
    public boolean deshacerMovimiento() {
        validarPartidaActiva();
        return cajaFuerte.deshacer();
    }

    /**
     * Reinicia el nivel actual al numero inicial sin descontar vidas.
     *
     * @throws IllegalStateException si la partida ya termino.
     */
    public void reiniciarNivel() {
        validarPartidaActiva();
        cajaFuerte.reiniciar();
    }

    /**
     * Avanza al siguiente nivel tras haber abierto la caja fuerte actual.
     * Si era el ultimo nivel de la lista, la partida queda marcada como terminada y ganada.
     *
     * @return {@code true} si se cargo un nuevo nivel; {@code false} si ya se completaron todos los niveles.
     * @throws IllegalStateException si la partida ya termino o la caja fuerte actual aun no esta abierta.
     */
    public boolean avanzarNivel() {
        validarPartidaActiva();
        if (!cajaFuerte.isAbierta()) {
            throw new IllegalStateException("Debe abrir la caja fuerte antes de avanzar de nivel");
        }
        if (indiceNivelActual + 1 < niveles.size()) {
            indiceNivelActual++;
            cajaFuerte = new CajaFuerte(niveles.get(indiceNivelActual));
            return true;
        }
        terminada = true;
        return false;
    }

    /**
     * Valida que la partida no haya terminado antes de permitir acciones.
     *
     * @throws IllegalStateException si {@code terminada} es {@code true}.
     */
    private void validarPartidaActiva() {
        if (terminada) {
            throw new IllegalStateException("La partida ya ha terminado");
        }
    }

    /**
     * Indica si la partida ha terminado (por victoria o por derrota).
     *
     * @return {@code true} si la partida termino; {@code false} si sigue activa.
     */
    public boolean isTerminada() {
        return terminada;
    }

    /**
     * Indica si el jugador gano la partida superando todos los niveles con vidas restantes.
     *
     * @return {@code true} si la partida termino en victoria.
     */
    public boolean isGanada() {
        return terminada && vidas > 0 && cajaFuerte.isAbierta();
    }

    /**
     * Indica si el jugador perdio la partida al quedarse sin vidas.
     *
     * @return {@code true} si la partida termino sin vidas.
     */
    public boolean isPerdida() {
        return terminada && vidas <= 0;
    }

    /**
     * Obtiene el nombre del jugador.
     *
     * @return nombre del jugador.
     */
    public String getNombreJugador() {
        return nombreJugador;
    }

    /**
     * Obtiene la cantidad de vidas restantes.
     *
     * @return numero de vidas disponibles.
     */
    public int getVidas() {
        return vidas;
    }

    /**
     * Obtiene el indice base cero del nivel actual.
     *
     * @return indice del nivel actual.
     */
    public int getIndiceNivelActual() {
        return indiceNivelActual;
    }

    /**
     * Obtiene el objeto {@link Nivel} que se esta jugando actualmente.
     *
     * @return nivel actual.
     */
    public Nivel getNivelActual() {
        return niveles.get(indiceNivelActual);
    }

    /**
     * Obtiene la instancia de {@link CajaFuerte} del nivel actual.
     *
     * @return caja fuerte activa.
     */
    public CajaFuerte getCajaFuerte() {
        return cajaFuerte;
    }

    /**
     * Obtiene el gestor de {@link Puntuacion} de la partida.
     *
     * @return puntuacion acumulada.
     */
    public Puntuacion getPuntuacion() {
        return puntuacion;
    }

    /**
     * Obtiene la lista inmutable de niveles configurados en la partida.
     *
     * @return lista de niveles.
     */
    public List<Nivel> getNiveles() {
        return niveles;
    }
}