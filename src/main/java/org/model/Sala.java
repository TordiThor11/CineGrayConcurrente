package org.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

public class Sala { //Cada sala del cine tiene 10x10 butacas
    private String nombre;
    private TreeMap<LocalDateTime, Pelicula> cronogramaPeliculas;

    private TreeMap<LocalDateTime, Butaca[][]> disponibilidadButacas;

    public Sala(String nombre) {
        this.nombre = nombre;
        this.cronogramaPeliculas = new TreeMap<>();
        this.disponibilidadButacas = new TreeMap<>();
    }

    private Butaca[][] inicializarButacas() {
        Butaca[][] butacas= new Butaca[10][10];
        for (int fila = 0; fila < butacas.length; fila++) {
            for (int col = 0; col < butacas[fila].length; col++) {
                butacas[fila][col] = new Butaca(fila, col);
            }
        }
        return butacas;
    }

    public boolean agregarFuncion(LocalDateTime fechaHora, Pelicula pelicula) {
        if (cronogramaPeliculas.containsKey(fechaHora)) {
            System.out.println("Error: Ya hay una función en ese horario.");
            return false;
        }
        cronogramaPeliculas.put(fechaHora, pelicula);
        disponibilidadButacas.put(fechaHora, inicializarButacas());
        return true;
    }

    public void mostrarFunciones() {
        if (cronogramaPeliculas.isEmpty()){
            System.out.println("No hay funciones en el cronograma");
        }else {
            System.out.println("Hay peliss");
            for (Map.Entry<LocalDateTime, Pelicula> entrada : cronogramaPeliculas.entrySet()) {
                System.out.println("En la fecha: " + entrada.getKey() + " se transmitira la peli -> " + entrada.getValue());
            }
        }
    }
    public void reservarButaca(LocalDateTime fechaHora, int fila, int columna) throws Exception {
        Butaca[][] butacas = disponibilidadButacas.get(fechaHora);
        butacas[fila][columna].reservar();
    }
}
