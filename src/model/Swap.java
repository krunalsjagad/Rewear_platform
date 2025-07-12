package model;

import java.sql.Timestamp;

public class Swap {
    private int id;
    private int itemId;
    private int requesterId;
    private String status;          // "Requested","Approved","Rejected","Expired"
    private Timestamp requestedAt;

    public Swap() { }

    public Swap(int id, int itemId, int requesterId,
                String status, Timestamp requestedAt) {
        this.id = id;
        this.itemId = itemId;
        this.requesterId = requesterId;
        this.status = status;
        this.requestedAt = requestedAt;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }

    public int getRequesterId() { return requesterId; }
    public void setRequesterId(int requesterId) { this.requesterId = requesterId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getRequestedAt() { return requestedAt; }
    public void setRequestedAt(Timestamp requestedAt) { this.requestedAt = requestedAt; }

    @Override
    public String toString() {
        return "Swap{" +
               "id=" + id +
               ", itemId=" + itemId +
               ", requesterId=" + requesterId +
               ", status='" + status + '\'' +
               ", requestedAt=" + requestedAt +
               '}';
    }
}
