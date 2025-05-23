package org.model;

import java.sql.Time;
import java.sql.Timestamp;

public class Reserva {  //No estoy seguro de usar este model. Tal vez solo sirva para enviar datos a la DB.
    private String usuario; //Puede ser cualquier otra forma de identificar al usuario que reservo
    private Butaca butaca;
    private Timestamp fechaDeReserva; // Timestamp(int year, int month, int date, int hour, int minute, int second, int nano)
    private boolean vigencia; //True=activo y false=cancelado
}
