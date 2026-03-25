package Control.Logica;

import Vista.VistaPrincipal;
import javax.swing.SwingUtilities;

/**
 *
 * @author Julian, Miguel, Andres
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VistaPrincipal(new MiniPigController()).setVisible(true);
        });
    }
}
