package sinDb.service;

import sinDb.domain.Butaca;
import sinDb.domain.EstadoButaca;
import sinDb.domain.Funcion;

import java.util.concurrent.locks.ReentrantLock;

public class ReservaService {
    private final SeatLockManager lockManager;

    public ReservaService(SeatLockManager lockManager) {
        this.lockManager = lockManager;
    }

    public boolean reservar(Funcion funcion, int fila, int columna) {
        String key = funcion.toString() + "-" + fila + "-" + columna;
        ReentrantLock lock = lockManager.getLock(key);
        lock.lock();
        try {
            Butaca b = funcion.getButaca(fila, columna);
            if (b.getEstado() != EstadoButaca.LIBRE) return false;
            b.setEstado(EstadoButaca.RESERVADA);
            return true;
        } finally {
            lock.unlock();
        }
    }
}
