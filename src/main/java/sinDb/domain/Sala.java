package sinDb.domain;

import java.time.LocalDateTime;
import java.util.*;

public class Sala {
    private final String nombre;
    private final TreeMap<LocalDateTime, Funcion> funciones = new TreeMap<>();

    public Sala(String nombre) { this.nombre = nombre; }

    public void agregarFuncion(Funcion f) {
        funciones.put(f.getHorario(), f);
    }

    public String getNombre() { return nombre; }

    public TreeMap<LocalDateTime, Funcion> getFunciones() {
        return funciones;
    }

    @Override
    public String toString() { return nombre; }
}
