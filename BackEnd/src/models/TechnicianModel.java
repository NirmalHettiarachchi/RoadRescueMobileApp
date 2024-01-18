package models;

import java.sql.Timestamp;

public class TechnicianModel {

    private String id;
    private String fName;
    private String lName;
    private String contact;
    private Timestamp timestamp;
    private String expertise;
    private String status;
    private int didJobs;


    public TechnicianModel(String id, String fName, String lName, String contact, Timestamp timestamp, String expertise, String status, int didJobs) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.contact = contact;
        this.timestamp = timestamp;
        this.expertise = expertise;
        this.status = status;
        this.didJobs = didJobs;
    }

    public TechnicianModel(String id, String fName, String lName, String contact, Timestamp timestamp, String status) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.contact = contact;
        this.timestamp = timestamp;
        this.status = status;
    }

    public TechnicianModel(String id, String fName, String lName, String contact, String expertise, String status, int didJobs) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.contact = contact;
        this.expertise = expertise;
        this.status = status;
        this.didJobs = didJobs;
    }

    public TechnicianModel(String fName, String lName, String contact, String status) {
        this.fName = fName;
        this.lName = lName;
        this.contact = contact;
        this.status = status;
    }

    public TechnicianModel() {
    }


    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDidJobs() {
        return didJobs;
    }

    public void setDidJobs(int didJobs) {
        this.didJobs = didJobs;
    }

    @Override
    public String toString() {
        return "TechnicianModel{" +
                "id='" + id + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", contact='" + contact + '\'' +
                ", timestamp=" + timestamp +
                ", expertise='" + expertise + '\'' +
                ", status='" + status + '\'' +
                ", didJobs=" + didJobs +
                '}';
    }
}
