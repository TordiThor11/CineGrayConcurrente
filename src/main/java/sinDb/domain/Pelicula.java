package sinDb.domain;

public class Pelicula {
    private final String nombre;
    public Pelicula(String nombre) { this.nombre = nombre; }
    @Override
    public String toString() { return nombre; }
}
