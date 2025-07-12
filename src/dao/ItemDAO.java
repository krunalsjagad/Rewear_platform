package dao;

import model.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDAO {

    // Create a new item and return generated ID
    public Optional<Integer> createItem(Item item) {
        String sql = "INSERT INTO Items "
                   + "(user_id, title, description, category, type, size, condition, points_cost) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, item.getUserId());
            ps.setString(2, item.getTitle());
            ps.setString(3, item.getDescription());
            ps.setString(4, item.getCategory());
            ps.setString(5, item.getType());
            ps.setString(6, item.getSize());
            ps.setString(7, item.getCondition());
            ps.setInt(8, item.getPointsCost());

            int affected = ps.executeUpdate();
            if (affected == 1) {
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

    // Fetch single item by ID (includes images)
    public Optional<Item> getItemById(int itemId) {
        String sql = "SELECT * FROM Items WHERE id = ?";
        String imgSql = "SELECT image_path FROM ItemImages WHERE item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, itemId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return Optional.empty();

            Item item = mapRowToItem(rs);
            // load images
            try (PreparedStatement imgPs = conn.prepareStatement(imgSql)) {
                imgPs.setInt(1, itemId);
                ResultSet imgRs = imgPs.executeQuery();
                List<String> paths = new ArrayList<>();
                while (imgRs.next()) paths.add(imgRs.getString("image_path"));
                item.setImagePaths(paths);
            }
            return Optional.of(item);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // List all approved items (with optional filters/pagination later)
    public List<Item> listApprovedItems() {
        String sql = "SELECT * FROM Items WHERE status = 'Approved' ORDER BY created_at DESC";
        List<Item> items = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                items.add(mapRowToItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // Update item status (e.g. to Approved, Rejected, Swapped)
    public boolean updateStatus(int itemId, String newStatus) {
        String sql = "UPDATE Items SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, itemId);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper to map a ResultSet row to an Item (without images)
    private Item mapRowToItem(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setId(rs.getInt("id"));
        item.setUserId(rs.getInt("user_id"));
        item.setTitle(rs.getString("title"));
        item.setDescription(rs.getString("description"));
        item.setCategory(rs.getString("category"));
        item.setType(rs.getString("type"));
        item.setSize(rs.getString("size"));
        item.setCondition(rs.getString("condition"));
        item.setPointsCost(rs.getInt("points_cost"));
        item.setStatus(rs.getString("status"));
        item.setCreatedAt(rs.getTimestamp("created_at"));
        return item;
    }
}
/**
 * Fetch all items uploaded by a specific user, newest first.
 * @param userId the uploader’s ID
 * @return list of Item
 */
public List<Item> getItemsByUser(int userId) {
    String sql = "SELECT * FROM Items WHERE user_id = ? ORDER BY created_at DESC";
    List<Item> items = new ArrayList<>();

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, userId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                items.add(mapRowToItem(rs));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return items;
}
/**
 * Search approved items with optional keyword, filters, and sorting.
 */
public List<Item> searchApprovedItems(
        String keyword,
        String category,
        String type,
        String size,
        String condition,
        String sortParam) {

    StringBuilder sql = new StringBuilder(
        "SELECT * FROM Items WHERE status = 'Approved'");
    List<Object> params = new ArrayList<>();

    if (keyword != null && !keyword.isBlank()) {
        sql.append(" AND (title LIKE ? OR description LIKE ?)");
        String kw = "%" + keyword.trim() + "%";
        params.add(kw);
        params.add(kw);
    }
    if (category != null && !category.isBlank() && !category.equals("All")) {
        sql.append(" AND category = ?");
        params.add(category);
    }
    if (type != null && !type.isBlank() && !type.equals("All")) {
        sql.append(" AND type = ?");
        params.add(type);
    }
    if (size != null && !size.isBlank() && !size.equals("All")) {
        sql.append(" AND size = ?");
        params.add(size);
    }
    if (condition != null && !condition.isBlank() && !condition.equals("All")) {
        sql.append(" AND condition = ?");
        params.add(condition);
    }

    // Sorting
    switch (sortParam) {
        case "size":
            sql.append(" ORDER BY size ASC");
            break;
        case "points":
            sql.append(" ORDER BY points_cost ASC");
            break;
        default:
            sql.append(" ORDER BY created_at DESC");
    }

    List<Item> items = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql.toString())) {

        // Bind parameters
        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                items.add(mapRowToItem(rs));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return items;
}
/**
 * Fetch all items with status = 'Pending'
 */
public List<Item> getPendingItems() {
    String sql = "SELECT * FROM Items WHERE status = 'Pending' ORDER BY created_at DESC";
    List<Item> list = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            list.add(mapRowToItem(rs));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}
