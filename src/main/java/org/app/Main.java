package org.app;

import org.model.Pelicula;
import org.model.Sala;
import org.ui.DisplayButacas;
import org.ui.MainFrame;

import javax.swing.*;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        inicializarDatos();
    }
    private static void inicializarDatos(){
        Sala salaInfantil = new Sala("Infantil");
        salaInfantil.mostrarFunciones();
        salaInfantil.agregarFuncion(LocalDateTime.now(), new Pelicula("Cars"));
        salaInfantil.agregarFuncion(LocalDateTime.of(2025, 2, 1, 14, 0), new Pelicula("Kung fu panda"));
        salaInfantil.agregarFuncion(LocalDateTime.of(2025, 2, 1, 14, 0), new Pelicula("Kung fu panda 2"));
        salaInfantil.mostrarFunciones();
        try {
            salaInfantil.reservarButaca(LocalDateTime.of(2025, 2, 1, 14, 0), 5, 5);
            salaInfantil.reservarButaca(LocalDateTime.of(2025, 2, 1, 14, 0), 5, 4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}