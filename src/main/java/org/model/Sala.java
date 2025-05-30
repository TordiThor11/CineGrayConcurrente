package org.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

public class Sala {
    private int id;
    private String nombre;
    private TreeMap<LocalDateTime, Funcion> funciones;
    private final int filas;
    private final int columnas;


    public Sala(int id, String nombre, int filas, int columnas) {
        this.id = id;
        this.nombre = nombre;
        this.filas = filas;
        this.columnas = columnas;
        this.funciones = new TreeMap<>();
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

    public void agregarFuncion(LocalDateTime fechaHora, Funcion funcion) throws Exception {
        if (funciones.containsKey(fechaHora)) {
            throw new Exception("Ya hay una función en ese horario");
        }
        funciones.put(fechaHora, funcion);
    }
    public void agregarFuncion(Funcion funcion) throws Exception {
        if (funciones.containsKey(funcion.getHorario())) {
            throw new Exception("Ya hay una función en ese horario");
        }
        funciones.put(funcion.getHorario(), funcion);
    }

    public void mostrarFunciones() {
        if (funciones.isEmpty()){
            System.out.println("No hay funciones en el cronograma");
        }else {
            System.out.println("Hay peliss");
            for (Map.Entry<LocalDateTime, Funcion> entrada : funciones.entrySet()) {
                System.out.println("En la fecha: " + entrada.getKey() + " se transmitira la peli -> " + entrada.getValue());
            }
        }
    }
    /*public void reservarButaca(LocalDateTime fechaHora, int fila, int columna) throws Exception {
        Butaca[][] butacas = disponibilidadButacas.get(fechaHora);
//        butacas[fila][columna].reservar();
    }*/
    public int getFilas(){return filas;}
    public int getColumnas(){return columnas;}
    public int getId() { return id; }
    @Override
    public String toString() {
        return "id:" + id + " nombre:" + nombre;
    }
}
