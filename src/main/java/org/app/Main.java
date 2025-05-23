package org.app;

import org.ui.DisplayButacas;
import org.ui.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Hello world!");
        // Levanta la ventana en el hilo de Swing
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}