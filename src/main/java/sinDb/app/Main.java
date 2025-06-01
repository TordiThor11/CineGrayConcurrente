package sinDb.app;

import sinDb.repository.CineRepository;
import sinDb.service.ReservaService;
import sinDb.service.SeatLockManager;
import sinDb.ui.ReservasGUI;
import sinDb.util.DataGenerator;

import javax.swing.*;
import java.util.concurrent.ExecutorService;

public class Main {
    public static void main(String[] args) {
        CineRepository repo = new CineRepository();
        DataGenerator.cargarDatos(repo);
        SeatLockManager lockManager = new SeatLockManager();
        ReservaService service = new ReservaService(lockManager);


        SwingUtilities.invokeLater(() -> new ReservasGUI(repo, service));
        SwingUtilities.invokeLater(() -> new ReservasGUI(repo, service));
    }


}
