package org.model;

import java.time.LocalDateTime;

public class Funcion {
    private int id;
    private LocalDateTime horario;
    private Pelicula pelicula;
    private Butaca[][] butacas;

    public Funcion(int id, LocalDateTime horario, Pelicula pelicula, int filas, int columnas) {
        this.id = id;
        this.horario = horario;
        this.pelicula = pelicula;
        this.butacas = new Butaca[filas][columnas];
        for (int f = 0; f < filas; f++) {
            for (int c = 0; c < columnas; c++) {
                butacas[f][c] = new Butaca(f, c);
            }
        }
    }

    public Butaca getButaca(int fila, int columna) {
        return butacas[fila][columna];
    }
    public int getId() { return id; }
    public Butaca[][] getButacas() { return butacas; }
    public LocalDateTime getHorario() { return horario; }

}
