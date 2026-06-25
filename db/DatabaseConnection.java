package rehlat.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String DATABASE_URL = "jdbc:sqlite:rehlat_umrah.db";
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(DATABASE_URL);
            System.out.println("Database connected");
        }
        return connection;
    }


    public static void initializeDatabase() {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();

            // One users table stores all user types.
            // Role-specific columns are filled depending on the user type.
            stmt.execute("CREATE TABLE IF NOT EXISTS users ("
                    + "user_id       INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "full_name     TEXT, "
                    + "email         TEXT UNIQUE, "
                    + "password      TEXT, "
                    + "phone         TEXT, "
                    + "role          TEXT, "
                    + "passport_num  TEXT, "
                    + "nationality   TEXT, "
                    + "agency_name   TEXT, "
                    + "license_num   TEXT, "
                    + "access_level  INTEGER DEFAULT 0)");

            stmt.execute("CREATE TABLE IF NOT EXISTS packages ("
                    + "package_id    INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "package_name  TEXT, "
                    + "hotel_makkah  TEXT, "
                    + "hotel_madinah TEXT, "
                    + "flight_details TEXT, "
                    + "num_nights    INTEGER, "
                    + "price         REAL, "
                    + "hotel_rating  INTEGER, "
                    + "depart_date   TEXT, "
                    + "available     INTEGER DEFAULT 1)");

            stmt.execute("CREATE TABLE IF NOT EXISTS bookings ("
                    + "booking_id  INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "pilgrim_id  INTEGER, "
                    + "package_id  INTEGER, "
                    + "status      TEXT DEFAULT 'PENDING', "
                    + "paid        INTEGER DEFAULT 0)");

            System.out.println("Tables ready.");

            // Insert default accounts and packages only on the very first run.
            seedDefaultData(conn);

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private static void seedDefaultData(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM users");
            rs.next();
            int count = rs.getInt("total");

            if (count == 0) {
                // Default Admin account
                stmt.execute("INSERT INTO users (full_name, email, password, phone, role, access_level) "
                        + "VALUES ('Admin User', 'admin@rehlat.com', 'admin123', '0900000000', 'Administrator', 1)");

                // Default Pilgrim account
                stmt.execute("INSERT INTO users (full_name, email, password, phone, role, passport_num, nationality) "
                        + "VALUES ('Test Pilgrim', 'pilgrim@rehlat.com', 'pilgrim123', '0911111111', 'Pilgrim', 'LB123456', 'Libyan')");

                // Default Travel Agent account
                stmt.execute("INSERT INTO users (full_name, email, password, phone, role, agency_name, license_num) "
                        + "VALUES ('Test Agent', 'agent@rehlat.com', 'agent123', '0922222222', 'TravelAgent', 'Al Noor Travel', 'LIC-001')");

                System.out.println("Default accounts created");
            }

            // Seed packages only if the table is empty
            ResultSet rsPkg = stmt.executeQuery("SELECT COUNT(*) AS total FROM packages");
            rsPkg.next();
            int pkgCount = rsPkg.getInt("total");

            if (pkgCount == 0) {
                stmt.execute("INSERT INTO packages (package_name, hotel_makkah, hotel_madinah, flight_details, num_nights, price, hotel_rating, depart_date, available) "
                        + "VALUES ('Economy Package', 'Dar Al Tawhid', 'Al Ansar Hotel', 'Libyan Airlines LY101', 10, 1500.0, 3, '2026-08-01', 1)");

                stmt.execute("INSERT INTO packages (package_name, hotel_makkah, hotel_madinah, flight_details, num_nights, price, hotel_rating, depart_date, available) "
                        + "VALUES ('Standard Package', 'Makkah Clock Royal', 'Movenpick Madinah', 'Qatar Airways QR500', 14, 2500.0, 4, '2026-08-15', 1)");

                stmt.execute("INSERT INTO packages (package_name, hotel_makkah, hotel_madinah, flight_details, num_nights, price, hotel_rating, depart_date, available) "
                        + "VALUES ('Premium Package', 'Conrad Makkah', 'Oberoi Madinah', 'Emirates EK303', 21, 4500.0, 5, '2026-09-01', 1)");

                System.out.println("Default packages created");
            }

        } catch (SQLException e) {
            System.out.println("Error seeding data:" + e.getMessage());
        }
    }


    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
