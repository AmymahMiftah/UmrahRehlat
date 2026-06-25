package rehlat.db;

import rehlat.model.Administrator;
import rehlat.model.Pilgrim;
import rehlat.model.TravelAgent;
import rehlat.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserRepository {

    public static User login(String email, String password) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM users WHERE email = ? AND password = ?");
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return buildUser(rs);
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
            return null;
        }
    }

    public static boolean savePilgrim(Pilgrim pilgrim) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO users (full_name, email, password, phone, role, passport_num, nationality) "
                    + "VALUES (?, ?, ?, ?, 'Pilgrim', ?, ?)");
            stmt.setString(1, pilgrim.getFullName());
            stmt.setString(2, pilgrim.getEmail());
            stmt.setString(3, pilgrim.getPassword());
            stmt.setString(4, pilgrim.getPhone());
            stmt.setString(5, pilgrim.getPassportNumber());
            stmt.setString(6, pilgrim.getNationality());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error saving pilgrim: " + e.getMessage());
            return false;
        }
    }

    public static boolean saveTravelAgent(TravelAgent agent) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO users (full_name, email, password, phone, role, agency_name, license_num) "
                    + "VALUES (?, ?, ?, ?, 'TravelAgent', ?, ?)");
            stmt.setString(1, agent.getFullName());
            stmt.setString(2, agent.getEmail());
            stmt.setString(3, agent.getPassword());
            stmt.setString(4, agent.getPhone());
            stmt.setString(5, agent.getAgencyName());
            stmt.setString(6, agent.getLicenseNumber());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error saving agent: " + e.getMessage());
            return false;
        }
    }

    public static boolean emailExists(String email) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT COUNT(*) AS total FROM users WHERE email = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("total") > 0;

        } catch (SQLException e) {
            System.out.println("Error checking email:" + e.getMessage());
            return false;
        }
    }

    public static ArrayList<User> getAllUsers() {
        ArrayList<User> list = new ArrayList<User>();
        try {
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                list.add(buildUser(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        return list;
    }

    private static User buildUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("user_id");
        String name = rs.getString("full_name");
        String email = rs.getString("email");
        String pass = rs.getString("password");
        String phone = rs.getString("phone");
        String role = rs.getString("role");

        if (role.equals("Administrator")) {
            int level = rs.getInt("access_level");
            return new Administrator(id, name, email, pass, phone, level);

        } else if (role.equals("TravelAgent")) {
            String agency = rs.getString("agency_name");
            String license = rs.getString("license_num");
            return new TravelAgent(id, name, email, pass, phone, agency, license);

        } else {
            String passport = rs.getString("passport_num");
            String nationality = rs.getString("nationality");
            return new Pilgrim(id, name, email, pass, phone, passport, nationality);
        }
    }
}
