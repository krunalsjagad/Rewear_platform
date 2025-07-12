package dao;

import model.Swap;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SwapDAO {

    // Request a swap (redeem points)
    public Optional<Integer> createSwap(int itemId, int requesterId) {
        String sql = "INSERT INTO Swaps (item_id, requester_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, itemId);
            ps.setInt(2, requesterId);
            int updated = ps.executeUpdate();
            if (updated == 1) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    return Optional.of(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Fetch swaps for a specific user
    public List<Swap> getSwapsByUser(int userId) {
        String sql = "SELECT * FROM Swaps WHERE requester_id = ? ORDER BY requested_at DESC";
        List<Swap> swaps = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                swaps.add(mapRowToSwap(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return swaps;
    }

    // Update swap status (Approved, Rejected, Expired)
    public boolean updateStatus(int swapId, String newStatus) {
        String sql = "UPDATE Swaps SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, swapId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper to map a ResultSet row to Swap
    private Swap mapRowToSwap(ResultSet rs) throws SQLException {
        Swap s = new Swap();
        s.setId(rs.getInt("id"));
        s.setItemId(rs.getInt("item_id"));
        s.setRequesterId(rs.getInt("requester_id"));
        s.setStatus(rs.getString("status"));
        s.setRequestedAt(rs.getTimestamp("requested_at"));
        return s;
    }
}
