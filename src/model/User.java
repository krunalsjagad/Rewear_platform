package model;

import java.sql.Timestamp;

public class User {
    private int id;
    private String name;
    private String email;
    private String passwordHash;
    private int points;
    private String role;          // "USER" or "ADMIN"
    private Timestamp createdAt;

    public User() { }

    public User(int id, String name, String email, String passwordHash,
                int points, String role, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.points = points;
        this.role = role;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", points=" + points +
               ", role='" + role + '\'' +
               ", createdAt=" + createdAt +
               '}';
    }
}
