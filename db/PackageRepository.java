package rehlat.db;

import rehlat.model.UmrahPackage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PackageRepository {

    public static ArrayList<UmrahPackage> getAllPackages() {
        ArrayList<UmrahPackage> list = new ArrayList<UmrahPackage>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM packages");

            while (rs.next()) {
                list.add(buildPackage(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error loading packages:" + e.getMessage());
        }
        return list;
    }

    public static boolean savePackage(UmrahPackage pkg) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO packages (package_name, hotel_makkah, hotel_madinah, "
                    + "flight_details, num_nights, price, hotel_rating, depart_date, available) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            stmt.setString(1, pkg.getPackageName());
            stmt.setString(2, pkg.getHotelMakkah());
            stmt.setString(3, pkg.getHotelMadinah());
            stmt.setString(4, pkg.getFlightDetails());
            stmt.setInt(5, pkg.getNumberOfNights());
            stmt.setDouble(6, pkg.getPrice());
            stmt.setInt(7, pkg.getHotelRating());
            stmt.setString(8, pkg.getDepartureDate());
            stmt.setInt(9, pkg.isAvailable() ? 1 : 0);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error saving package: " + e.getMessage());
            return false;
        }
    }

    private static UmrahPackage buildPackage(ResultSet rs) throws SQLException {
        int id = rs.getInt("package_id");
        String name = rs.getString("package_name");
        String makkah = rs.getString("hotel_makkah");
        String madinah = rs.getString("hotel_madinah");
        String flight = rs.getString("flight_details");
        int nights = rs.getInt("num_nights");
        double price = rs.getDouble("price");
        int rating = rs.getInt("hotel_rating");
        String date = rs.getString("depart_date");
        boolean available = rs.getInt("available") == 1;

        return new UmrahPackage(id, name, makkah, madinah, flight, nights, price, rating, date, available);
    }
}
