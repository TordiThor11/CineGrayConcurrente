package org.model;

public class Pelicula {
    private String nombre;

    public Pelicula(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
