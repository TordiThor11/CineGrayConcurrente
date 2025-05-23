package org.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

public class Sala { //Cada sala del cine tiene 10x10 butacas
    private String nombre;
    private TreeMap<LocalDateTime, Pelicula> cronogramaPeliculas;
    private Butaca butacas;

    public Sala(String nombre) {
        this.nombre = nombre;
        this.cronogramaPeliculas = new TreeMap<>();
    }
    public boolean agregarFuncion(LocalDateTime fechaHora, Pelicula pelicula) {
        if (cronogramaPeliculas.containsKey(fechaHora)) {
            System.out.println("Error: Ya hay una función en ese horario.");
            return false;
        }
        cronogramaPeliculas.put(fechaHora, pelicula);
        return true;
    }

    public void mostrarFunciones() {
        for (Map.Entry<LocalDateTime, Pelicula> entrada : cronogramaPeliculas.entrySet()) {
            System.out.println(entrada.getKey() + " -> " + entrada.getValue());
        }
    }
}
