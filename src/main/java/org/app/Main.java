package org.app;

import org.dao.SalaDAO;
import org.model.Pelicula;
import org.model.Sala;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        try{
//            inicializarDatos();
            SalaDAO salaDAO = new SalaDAO();
            var salas = salaDAO.getAll();
            for (Sala sala : salas) {
                System.out.println(sala.toString());
            }
            var cronograma = salaDAO.getCronogramaPeliculas(salas.get(1).getNombre());
            for (LocalDateTime horario : cronograma.keySet()) {
                System.out.println("A las " + horario + " esta la pelicula " + cronograma.get(horario).toString());
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
    private static void inicializarDatos() throws Exception {
        Sala salaInfantil = new Sala("Infantil");
        salaInfantil.mostrarFunciones();
        salaInfantil.agregarFuncion(LocalDateTime.now(), new Pelicula("Cars"));
        salaInfantil.agregarFuncion(LocalDateTime.of(2025, 2, 1, 14, 0), new Pelicula("Kung fu panda"));
        salaInfantil.agregarFuncion(LocalDateTime.of(2025, 2, 1, 14, 0), new Pelicula("Kung fu panda 2"));
        salaInfantil.mostrarFunciones();
        salaInfantil.reservarButaca(LocalDateTime.of(2025, 2, 1, 14, 0), 5, 5);
        salaInfantil.reservarButaca(LocalDateTime.of(2025, 2, 1, 14, 0), 5, 4);
    }
}