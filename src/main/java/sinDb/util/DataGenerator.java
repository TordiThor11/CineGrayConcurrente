package sinDb.util;

import sinDb.domain.Funcion;
import sinDb.domain.Pelicula;
import sinDb.domain.Sala;
import sinDb.repository.CineRepository;

import java.time.LocalDateTime;

public class DataGenerator {
    public static void cargarDatos(CineRepository repo) {
        Sala sala1 = new Sala("Sala A");
        Pelicula p1 = new Pelicula("Matrix");
        Pelicula p2 = new Pelicula("Shrek");

        sala1.agregarFuncion(new Funcion(LocalDateTime.now().plusHours(1), p1, 5, 8));
        sala1.agregarFuncion(new Funcion(LocalDateTime.now().plusHours(3), p2, 5, 8));

        repo.agregarSala(sala1);
    }
}
