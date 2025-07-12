package dao;

import model.User;
import utils.PasswordUtil;
import java.sql.*;
import java.util.Optional;

public class UserDAO {

    // Create a new user (signup)
    public boolean createUser(String name, String email, String plainPassword) {
        String sql = "INSERT INTO Users (name, email, password_hash) VALUES (?, ?, ?)";
        String hashed = PasswordUtil.hash(plainPassword);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, hashed);
            return ps.executeUpdate() == 1;

        } catch (SQLIntegrityConstraintViolationException e) {
            // Duplicate email
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Validate login; returns Optional<User> if credentials match
    public Optional<User> validateUser(String email, String plainPassword) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        String hashed = PasswordUtil.hash(plainPassword);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getString("password_hash").equals(hashed)) {
                User u = mapRowToUser(rs);
                return Optional.of(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Fetch user by ID
    public Optional<User> getUserById(int id) {
        String sql = "SELECT * FROM Users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRowToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Update user's points (positive or negative delta)
    public boolean updatePoints(int userId, int delta) {
        String sql = "UPDATE Users SET points = points + ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, delta);
            ps.setInt(2, userId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Map a ResultSet row to a User object
    private User mapRowToUser(ResultSet rs) throws SQLException {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setName(rs.getString("name"));
        u.setEmail(rs.getString("email"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setPoints(rs.getInt("points"));
        u.setRole(rs.getString("role"));
        u.setCreatedAt(rs.getTimestamp("created_at"));
        return u;
    }
}
