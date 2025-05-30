package org.model;

public class Pelicula {
    private int id;
    private String nombre;

    public Pelicula(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
