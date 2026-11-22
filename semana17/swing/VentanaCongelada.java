package semana17.swing;

import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class VentanaCongelada extends JFrame {
    private final JLabel lblEstado = new JLabel("Esperando...");
    private final JButton btnReporte = new JButton("Generar reporte");

    public VentanaCongelada() {
        super("Reporte - versión congelada");
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 20));

        lblEstado.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnReporte.addActionListener(e -> generar());

        add(btnReporte);
        add(lblEstado);

        setSize(380, 120);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void generar() {
        // Demostración intencional: bloquea el EDT durante tres segundos.
        lblEstado.setText("Procesando...");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            lblEstado.setText("Proceso interrumpido");
            return;
        }
        lblEstado.setText("Reporte listo");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () -> new VentanaCongelada().setVisible(true)
        );
    }
}
