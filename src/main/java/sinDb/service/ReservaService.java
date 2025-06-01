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
        System.out.println("Hilo actual: "+ Thread.currentThread().getName() + " -- Bloqueo de -Funcion:" + funcion +" -Fila:" + fila + " -Columna:" + columna);
        try {
            Butaca b = funcion.getButaca(fila, columna);
            if (b.getEstado() != EstadoButaca.LIBRE) return false;
            Thread.sleep(300); // simula tiempo de escritura
            b.setEstado(EstadoButaca.RESERVADA);
            System.out.println("Hilo actual: "+ Thread.currentThread().getName() + " -- Reserva de -Funcion:" + funcion +" -Fila:" + fila + " -Columna:" + columna);
            return true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Hilo actual: "+ Thread.currentThread().getName() + " -- Desbloqueo de -Funcion:" + funcion +" -Fila:" + fila + " -Columna:" + columna);
            lock.unlock();
        }
    }
}
