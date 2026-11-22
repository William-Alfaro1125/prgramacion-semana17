package semana17.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class VentanaFluida extends JFrame {
    private final JLabel lblEstado = new JLabel("Esperando...");
    private final JButton btnReporte = new JButton("Generar reporte");
    private final JProgressBar barra = new JProgressBar(0, 10);

    public VentanaFluida() {
        super("Reporte - versión con hilo");
        setLayout(new BorderLayout(10, 12));

        lblEstado.setFont(new Font("SansSerif", Font.BOLD, 15));
        barra.setStringPainted(true);

        JPanel panelBoton = new JPanel(new FlowLayout());
        panelBoton.add(btnReporte);

        add(lblEstado, BorderLayout.NORTH);
        add(barra, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);

        btnReporte.addActionListener(e -> generar());

        setSize(420, 160);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void generar() {
        btnReporte.setEnabled(false);
        barra.setValue(0);
        lblEstado.setText("Procesando...");

        Thread trabajador = new Thread(() -> {
            for (int paso = 1; paso <= 10; paso++) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    SwingUtilities.invokeLater(() -> {
                        lblEstado.setText("Proceso interrumpido");
                        btnReporte.setEnabled(true);
                    });
                    return;
                }

                int avance = paso;
                SwingUtilities.invokeLater(() -> barra.setValue(avance));
            }

            SwingUtilities.invokeLater(() -> {
                lblEstado.setText("Reporte listo");
                btnReporte.setEnabled(true);
            });
        }, "hilo-trabajador");

        trabajador.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> new VentanaFluida().setVisible(true)
        );
    }
}
