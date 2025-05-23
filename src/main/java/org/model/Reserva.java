package org.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Reserva {
    private String usuario; //Puede ser cualquier otra forma de identificar al usuario que reservo
    private Sala sala;
    private Butaca butaca;
    private LocalDateTime fechaDeReserva;
    private boolean vigencia; //True=activo/reservado y false=cancelado
}
