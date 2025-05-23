package org.ui;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SeatMapPanel extends JPanel {
    private final SeatButton[][] seats = new SeatButton[10][10];

    public SeatMapPanel() {
        super(new GridLayout(10, 10, 2, 2));  // 2px de separación
        initializeSeats();
    }

    private void initializeSeats() {
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                SeatButton btn = new SeatButton(r, c);
                btn.setToolTipText("Fila " + (r+1) + ", Asiento " + (c+1));
                btn.addActionListener(e -> onSeatClicked(btn));
                seats[r][c] = btn;
                add(btn);
            }
        }
    }

    private void onSeatClicked(SeatButton btn) {
        // Toggle selección local
        if (btn.getState() == SeatButton.SeatState.AVAILABLE) {
            btn.setState(SeatButton.SeatState.SELECTED);
        } else if (btn.getState() == SeatButton.SeatState.SELECTED) {
            btn.setState(SeatButton.SeatState.AVAILABLE);
        }
        // Aquí podrías habilitar un botón “Confirmar reserva”
    }

    /**
     * Devuelve la lista de butacas actualmente marcadas como SELECTED.
     * Luego tu UI puede pasar estas coordenadas al servicio.
     */
    public List<SeatButton> getSelectedSeats() {
        List<SeatButton> sel = new ArrayList<>();
        for (var row : seats)
            for (var b : row)
                if (b.getState() == SeatButton.SeatState.SELECTED)
                    sel.add(b);
        return sel;
    }

    /**
     * Permite, desde tu capa de servicio, marcar como RESERVED los ya ocupados:
     */
    public void markReserved(List<Point> reservedCoords) {
        for (Point p : reservedCoords) {
            seats[p.x][p.y].setState(SeatButton.SeatState.RESERVED);
        }
    }
}
