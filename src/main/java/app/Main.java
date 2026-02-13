package app;

import javax.swing.SwingUtilities;
import view.TelaPrincipal;

public class Main {

    public static void main(String[] args) {

        // Garante que a interface gráfica rode na thread correta do Swing
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });

    }
}
