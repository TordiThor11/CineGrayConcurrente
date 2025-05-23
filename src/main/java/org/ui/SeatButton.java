package org.ui;

import javax.swing.JButton;

public class SeatButton extends JButton {
    private final int row, col;
    private SeatState state;

    public enum SeatState { AVAILABLE, SELECTED, RESERVED }

    public SeatButton(int row, int col) {
        super();  // luego le ponemos texto o icono
        this.row = row;
        this.col = col;
        setState(SeatState.AVAILABLE);
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    public SeatState getState() {
        return state;
    }

    public void setState(SeatState newState) {
        this.state = newState;
        switch (newState) {
            case AVAILABLE  -> { setText("");       setEnabled(true);  }
            case SELECTED   -> { setText("✔");      setEnabled(true);  }
            case RESERVED   -> { setText("✖");      setEnabled(false); }
        }
    }
}
