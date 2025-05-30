package org.service;

import org.dao.ButacaDAO;
import org.model.EstadoButaca;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

public class ReservaService {
    private final ButacaDAO butacaDAO;
//    private final FuncionDAO funcionDAO;
    private final SeatLockManager lockManager;

    public ReservaService(ButacaDAO butacaDAO, SeatLockManager lockManager) {
        this.butacaDAO = butacaDAO;
//        this.funcionDAO = funcionDAO;
        this.lockManager = lockManager;
    }

    public boolean reservarButaca(int idFuncion, int fila, int columna) throws SQLException {
        String key = idFuncion + "-" + fila + "-" + columna;

        // 🔐 Exclusion mutua en memoria
        ReentrantLock lock = lockManager.getLock(key);
        lock.lock();
        try (Connection conn = butacaDAO.getConnection()) {
            conn.setAutoCommit(false);

            // 🕵️ Verificar si ya está reservada
            if (butacaDAO.existeReserva(conn, idFuncion, fila, columna)) {
                conn.rollback();
                return false; // ya reservada
            }

            // 📝 Insertar reserva
            butacaDAO.insertarReserva(conn, idFuncion, fila, columna, EstadoButaca.RESERVADA);
            conn.commit();
            return true;
        } catch (SQLException e) {
            throw e;
        } finally {
            lock.unlock();
        }
    }
}
