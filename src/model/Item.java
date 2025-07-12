package model;

import java.sql.Timestamp;
import java.util.List;

public class Item {
    private int id;
    private int userId;
    private String title;
    private String description;
    private String category;      // "Men","Women","Kids","Unisex"
    private String type;          // e.g. "Shirt", "Pants"
    private String size;          // "XS","S","M","L","XL","XXL"
    private String condition;     // "New","Like New", etc.
    private int pointsCost;
    private String status;        // "Pending","Approved","Swapped","Rejected"
    private Timestamp createdAt;
    private List<String> imagePaths;  // 0–5 image URLs or file paths

    public Item() { }

    public Item(int id, int userId, String title, String description,
                String category, String type, String size, String condition,
                int pointsCost, String status, Timestamp createdAt,
                List<String> imagePaths) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.type = type;
        this.size = size;
        this.condition = condition;
        this.pointsCost = pointsCost;
        this.status = status;
        this.createdAt = createdAt;
        this.imagePaths = imagePaths;
    }

    // Getters and setters for all fields
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public int getPointsCost() { return pointsCost; }
    public void setPointsCost(int pointsCost) { this.pointsCost = pointsCost; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public List<String> getImagePaths() { return imagePaths; }
    public void setImagePaths(List<String> imagePaths) { this.imagePaths = imagePaths; }

    @Override
    public String toString() {
        return "Item{" +
               "id=" + id +
               ", userId=" + userId +
               ", title='" + title + '\'' +
               ", category='" + category + '\'' +
               ", size='" + size + '\'' +
               ", status='" + status + '\'' +
               ", createdAt=" + createdAt +
               '}';
    }
}
