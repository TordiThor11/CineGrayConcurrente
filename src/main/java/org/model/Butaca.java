package org.model;

public class Butaca {

    //Existe una butaca por funcion. No por sala.
    private int fila;
    private int columna;
    private boolean disponible;



    public Butaca(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.disponible = true;
    }

    public void reservar() throws Exception {
        if (!disponible){
            throw new Exception("La butaca ya esta reservada");
        }
        disponible = false;
    }
}
