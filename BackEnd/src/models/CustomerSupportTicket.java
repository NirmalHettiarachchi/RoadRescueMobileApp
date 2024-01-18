package models;

public class CustomerSupportTicket {
    private int ticketId;
    private int customerId;
    private int supportMemberId;


    public CustomerSupportTicket(int ticketId, int customerId, int supportMemberId) {
        this.ticketId = ticketId;
        this.customerId = customerId;
        this.supportMemberId = supportMemberId;
    }

    public CustomerSupportTicket() {
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getSupportMemberId() {
        return supportMemberId;
    }

    public void setSupportMemberId(int supportMemberId) {
        this.supportMemberId = supportMemberId;
    }
}
