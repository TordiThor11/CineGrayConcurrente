package sinDb.domain;

public class Butaca {
    private final int fila;
    private final int columna;
    private EstadoButaca estado;

    public Butaca(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.estado = EstadoButaca.LIBRE;
    }

    public int getFila() { return fila; }
    public int getColumna() { return columna; }
    public EstadoButaca getEstado() { return estado; }
    public void setEstado(EstadoButaca estado) { this.estado = estado; }
}
