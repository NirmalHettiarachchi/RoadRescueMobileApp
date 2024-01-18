package models;

import java.sql.Timestamp;

public class SupportTicket {
    private int ticketId;
    private String title;
    private String description;
    private String ticketStatus;
    private String timestamp;



    public SupportTicket() {
    }

    public SupportTicket(int ticketId, String title, String description, String ticketStatus, String timestamp) {
        this.ticketId = ticketId;
        this.title = title;
        this.description = description;
        this.ticketStatus = ticketStatus;
        this.timestamp = timestamp;
    }


    public SupportTicket(int ticketId, String title, String ticketStatus, String timestamp) {
        this.ticketId = ticketId;
        this.title = title;
        this.ticketStatus = ticketStatus;
        this.timestamp = timestamp;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "SupportTicket{" +
                "ticketId=" + ticketId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", ticketStatus='" + ticketStatus + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
