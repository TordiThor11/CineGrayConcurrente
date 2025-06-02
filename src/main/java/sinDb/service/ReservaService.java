package sinDb.service;

import sinDb.domain.Butaca;
import sinDb.domain.EstadoButaca;
import sinDb.domain.Funcion;

import java.util.concurrent.locks.ReentrantLock;

public class ReservaService {
    // se usa el SeatLockManager para organizar los bloqueos de butacas, garantizando un bloqueo por butaca segun su key.
    // la key de la butaca esta formada por el nombre de la funcion, la fila y columna.
    private final SeatLockManager lockManager;

    public ReservaService(SeatLockManager lockManager) {
        this.lockManager = lockManager;
    }

    //La funcion reservar es el corazon de la concurrencia en el sistema. Es donde esta ubicada la region critica en donde se tiene que garantizar exclusion mutua.
    public boolean reservar(Funcion funcion, int fila, int columna) {
        String key = funcion.toString() + "-" + fila + "-" + columna;
        ReentrantLock lock = lockManager.getLock(key);
        lock.lock(); //Bloquea la region critica con la funcion lock() de la clase ReestrantLock
        System.out.println("Hilo actual: "+ Thread.currentThread().getName() + " -- Bloqueo de -Funcion:" + funcion +" -Fila:" + fila + " -Columna:" + columna);
        try {
            Butaca b = funcion.getButaca(fila, columna);
            if (b.getEstado() != EstadoButaca.LIBRE) return false;
            Thread.sleep(300); // simula tiempo de escritura, es solamente para demostrar claramente la concurrencia en simulacion.
            b.setEstado(EstadoButaca.RESERVADA);
            System.out.println("Hilo actual: "+ Thread.currentThread().getName() + " -- Reserva de -Funcion:" + funcion +" -Fila:" + fila + " -Columna:" + columna);
            return true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Hilo actual: "+ Thread.currentThread().getName() + " -- Desbloqueo de -Funcion:" + funcion +" -Fila:" + fila + " -Columna:" + columna);
            lock.unlock(); //desbloquea la region critica con la funcion lock() de la clase ReestrantLock
        }
    }
}
