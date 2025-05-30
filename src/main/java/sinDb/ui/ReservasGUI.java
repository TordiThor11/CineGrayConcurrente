package sinDb.ui;

import sinDb.domain.Butaca;
import sinDb.domain.EstadoButaca;
import sinDb.domain.Funcion;
import sinDb.domain.Sala;
import sinDb.repository.CineRepository;
import sinDb.service.ReservaService;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReservasGUI extends JFrame {
    private final JComboBox<Sala> comboSalas = new JComboBox<>();
    private final JComboBox<Funcion> comboFunciones = new JComboBox<>();
    private final JPanel panelButacas = new JPanel();

    private final CineRepository repo;
    private final ReservaService reservaService;
    private final ExecutorService executor = Executors.newFixedThreadPool(20);

    public ReservasGUI(CineRepository repo, ReservaService reservaService) {
        this.repo = repo;
        this.reservaService = reservaService;
        setTitle("Reservas Cine");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new GridLayout(1, 2));
        top.add(comboSalas);
        top.add(comboFunciones);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(panelButacas), BorderLayout.CENTER);

        repo.getTodasLasSalas().forEach(comboSalas::addItem);
        comboSalas.addActionListener(e -> cargarFunciones());
        comboFunciones.addActionListener(e -> mostrarButacas());

        setVisible(true);
    }

    private void cargarFunciones() {
        comboFunciones.removeAllItems();
        Sala sala = (Sala) comboSalas.getSelectedItem();
        if (sala != null) sala.getFunciones().values().forEach(comboFunciones::addItem);
    }

    private void mostrarButacas() {
        panelButacas.removeAll();
        Funcion f = (Funcion) comboFunciones.getSelectedItem();
        if (f == null) return;

        Butaca[][] m = f.getButacas();
        panelButacas.setLayout(new GridLayout(m.length, m[0].length));

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                Butaca b = m[i][j];
                JButton btn = new JButton(b.getEstado().name().substring(0, 1));
                btn.setBackground(b.getEstado() == EstadoButaca.LIBRE ? Color.GREEN : Color.RED);
                int fila = i, col = j;
                btn.addActionListener(e -> {
                    executor.submit(() -> {
                        boolean ok = reservaService.reservar(f, fila, col);
                        SwingUtilities.invokeLater(() -> {
                            if (ok) {
                                btn.setText("R");
                                btn.setBackground(Color.RED);
                            } else {
                                JOptionPane.showMessageDialog(this, "Ya reservada");
                            }
                        });
                    });
                });
                panelButacas.add(btn);
            }
        }
        panelButacas.revalidate();
        panelButacas.repaint();
    }
}
