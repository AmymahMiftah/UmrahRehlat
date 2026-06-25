package rehlat;

import rehlat.db.DatabaseConnection;
import rehlat.gui.LoginForm;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        DatabaseConnection.initializeDatabase();

        SwingUtilities.invokeLater(() -> new LoginForm());
    }
}
