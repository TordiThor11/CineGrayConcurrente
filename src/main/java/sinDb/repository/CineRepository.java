package sinDb.repository;

import sinDb.domain.Sala;

import java.util.ArrayList;
import java.util.List;

public class CineRepository {
    private final List<Sala> salas = new ArrayList<>();

    public void agregarSala(Sala s) { salas.add(s); }

    public List<Sala> getTodasLasSalas() { return salas; }
}
