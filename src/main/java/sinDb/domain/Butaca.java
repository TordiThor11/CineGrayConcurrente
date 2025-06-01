package sinDb.domain;

import sinDb.service.ButacaObserver;

import java.util.ArrayList;
import java.util.List;

public class Butaca {
    private final int fila;
    private final int columna;
    private EstadoButaca estado;

    private final List<ButacaObserver> observers = new ArrayList<>();


    public Butaca(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.estado = EstadoButaca.LIBRE;
    }
    public void agregarObserver(ButacaObserver obs) {
        observers.add(obs);
    }

    private void notificar() {
        for (ButacaObserver obs : observers) {
            obs.onButacaActualizada(this);
        }
    }

    public int getFila() { return fila; }
    public int getColumna() { return columna; }
    public EstadoButaca getEstado() { return estado; }
    public void setEstado(EstadoButaca estado) {
        this.estado = estado;
        notificar();
    }
}
