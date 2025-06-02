package sinDb.ui;

import sinDb.domain.Butaca;
import sinDb.domain.EstadoButaca;
import sinDb.domain.Funcion;
import sinDb.domain.Sala;
import sinDb.repository.CineRepository;
import sinDb.service.ReservaService;
import sinDb.service.ButacaObserver;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ReservasGUI extends JFrame {
    private final JComboBox<Sala> comboSalas = new JComboBox<>();
    private final JComboBox<Funcion> comboFunciones = new JComboBox<>();
    private final JPanel panelButacas = new JPanel();

    private final CineRepository repo;
    private final ReservaService reservaService;

    //La instancia executor de la clase ExecutorService es quien crea los hilos en simulacion o al reservar una butaca.
    private final ExecutorService executor = Executors.newFixedThreadPool(50); //El cine tiene 50 pcs para reservar.


    public ReservasGUI(CineRepository repo, ReservaService reservaService) {
        this.repo = repo;
        this.reservaService = reservaService;
        setTitle("Reservas Cine");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(1, 4));

        topPanel.add(comboSalas);
        topPanel.add(comboFunciones);
        //  Botón de refresco manual
        JButton btnRefrescar = new JButton("🔄 Refrescar");
        btnRefrescar.addActionListener(e -> mostrarButacas());
        topPanel.add(btnRefrescar);

        //  Nuevo botón: Simular 50 PCs
        JButton btnSimular = new JButton("🖥️ Simular 50 PCs");
        btnSimular.addActionListener(e -> simularReservaConcurrente());
        topPanel.add(btnSimular);


        add(topPanel, BorderLayout.NORTH);
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

                // AGREGÁS el observador para actualizar automáticamente el botón
                b.agregarObserver(crearObservadorParaBoton(btn));

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
    private ButacaObserver crearObservadorParaBoton(JButton btn) {
        return butaca -> SwingUtilities.invokeLater(() -> {
            btn.setText(butaca.getEstado().name().substring(0, 1));
            btn.setBackground(butaca.getEstado() == EstadoButaca.LIBRE ? Color.GREEN : Color.RED);
            btn.setEnabled(butaca.getEstado() == EstadoButaca.LIBRE); // opcional
        });
    }
    private void simularReservaConcurrente() {
        Funcion funcion = (Funcion) comboFunciones.getSelectedItem();
        if (funcion == null) {
            JOptionPane.showMessageDialog(this, "Seleccioná una función primero");
            return;
        }

        Butaca[][] matriz = funcion.getButacas();
        int filas = matriz.length;
        int columnas = matriz[0].length;
        Random random = new Random();

        System.out.println("🖥️ Simulando 50 computadoras reservando...");

        for (int i = 0; i < 50; i++) {
            int pcId = i;
            executor.submit(() -> {
                try {
                    Thread.sleep(500 + random.nextInt(500)); // 500-1000ms por hilo
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                boolean reservado = false;
                int intentos = 0;

                while (!reservado && intentos < 100) {
                    int fila = random.nextInt(filas);
                    int col = random.nextInt(columnas);
                    reservado = reservaService.reservar(funcion, fila, col);
                    intentos++;

                    if (reservado) {
                        System.out.printf("✅ PC %02d reservó [%d,%d]%n", pcId, fila, col);
                    }
                }

                if (!reservado) {
                    System.out.printf("❌ PC %02d no encontró butaca libre%n", pcId);
                }
            });
        }
    }




}
