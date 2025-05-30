package org.app;

import org.dao.ButacaDAO;
import org.dao.SalaDAO;
import org.model.Butaca;
import org.model.Funcion;
import org.model.Pelicula;
import org.model.Sala;
import org.service.ReservaService;
import org.service.SeatLockManager;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        try{
//            inicializarDatos();

            ButacaDAO butacaDAO = new ButacaDAO();
            Funcion funcion1 = new Funcion(1,LocalDateTime.of(2025, 2, 1, 14, 0), new Pelicula(1,"Kung fu panda"),10, 10);
            butacaDAO.cargarEstadosDeButacas(funcion1);
            var butacas = funcion1.getButacas();
            for (Butaca[] butaca : butacas) {
                for (Butaca butaca1 : butaca) {
                    System.out.println(butaca1.toString());
                }
            }

            SalaDAO salaDAO = new SalaDAO();
            var salas = salaDAO.getTodasLasSalas();
            for (Sala sala : salas) {
                System.out.println(sala.toString());
            }
            Sala sala1 = salas.getFirst();
            salaDAO.cargarFuncionesDeSala(sala1);
            sala1.mostrarFunciones();

            //reserva
            ReservaService reservaService = new ReservaService(new ButacaDAO(), new SeatLockManager());
            boolean exito = reservaService.reservarButaca(1, 1, 1);
            if (exito) {
                System.out.println("Reserva exitosa");
            } else {
                System.out.println("Butaca ya reservada");
            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
    private static void inicializarDatos() throws Exception {
        Sala salaInfantil = new Sala(1,"Infantil",10,10);
        salaInfantil.mostrarFunciones();
        salaInfantil.agregarFuncion(LocalDateTime.of(2025, 2, 1, 14, 0), new Funcion(1,LocalDateTime.of(2025, 2, 1, 14, 0), new Pelicula(1,"Kung fu panda"), salaInfantil.getFilas(), salaInfantil.getColumnas()));
        salaInfantil.agregarFuncion(LocalDateTime.of(2025, 2, 1, 18, 0), new Funcion(2,LocalDateTime.of(2025, 2, 1, 18, 0), new Pelicula(2,"Kung fu panda 2"), salaInfantil.getFilas(), salaInfantil.getColumnas()));
        salaInfantil.mostrarFunciones();
//        salaInfantil.reservarButaca(LocalDateTime.of(2025, 2, 1, 14, 0), 5, 5);
//        salaInfantil.reservarButaca(LocalDateTime.of(2025, 2, 1, 14, 0), 5, 4);
    }
}