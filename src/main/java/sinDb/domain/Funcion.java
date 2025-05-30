package sinDb.domain;

import java.time.LocalDateTime;

public class Funcion {
    private final LocalDateTime horario;
    private final Pelicula pelicula;
    private final Butaca[][] butacas;

    public Funcion(LocalDateTime horario, Pelicula pelicula, int filas, int columnas) {
        this.horario = horario;
        this.pelicula = pelicula;
        this.butacas = new Butaca[filas][columnas];
        for (int f = 0; f < filas; f++)
            for (int c = 0; c < columnas; c++)
                butacas[f][c] = new Butaca(f, c);
    }

    public LocalDateTime getHorario() { return horario; }
    public Pelicula getPelicula() { return pelicula; }
    public Butaca[][] getButacas() { return butacas; }
    public Butaca getButaca(int f, int c) { return butacas[f][c]; }

    @Override
    public String toString() {
        return pelicula + " - " + horario.toString();
    }
}
