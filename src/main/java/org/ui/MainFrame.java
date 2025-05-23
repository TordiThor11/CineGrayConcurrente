package org.ui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.util.List;
import java.awt.Point;

public class MainFrame extends JFrame {
    private final SeatMapPanel seatMap;
    private final JButton btnReserve;

    public MainFrame() {
        super("Reserva de Butacas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 650);
        setLocationRelativeTo(null);

        seatMap   = new SeatMapPanel();
        btnReserve = new JButton("Confirmar Reserva");
//        btnReserve.addActionListener(e -> doReserve());                   LOGICARESERVAAAAAAAAAAAAA

        add(seatMap,   BorderLayout.CENTER);
        add(btnReserve, BorderLayout.SOUTH);
    }
    /*
    private void doReserve() {
        // 1) Recoge butacas seleccionadas
        List<SeatButton> selected = seatMap.getSelectedSeats();

        if (selected.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona al menos una butaca.");
            return;
        }

        // 2) Convierte a coordenadas / modelo de negocio
        List<Point> coords = selected.stream()
                .map(b -> new Point(b.getRow(), b.getCol()))
                .toList();

        // 3) Llama a tu capa de servicio
        try {
            // SeatService es tu clase en package service
            SeatService service = new SeatService();
            service.reserveSeats(coords);

            // 4) Marca las reservadas en el mapa
            seatMap.markReserved(coords);
            JOptionPane.showMessageDialog(this, "Reservado con éxito.");
        } catch (BusinessException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                    "Fallo en reserva",
                    JOptionPane.ERROR_MESSAGE);
        }
    }*/
}
