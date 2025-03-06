package Application.Model;

public class ApplicationStatus {
    private int applicationStatusId;
    private String applicationStatus;
    private String description;

    // Default constructor
    public ApplicationStatus() {}

    // Parameterized constructor
    public ApplicationStatus(int applicationStatusId, String applicationStatus, 
                           String description) {
        this.applicationStatusId = applicationStatusId;
        this.applicationStatus = applicationStatus;
        this.description = description;
    }

    // Constructor without ID for creation
    public ApplicationStatus(String applicationStatus, String description) {
        this.applicationStatus = applicationStatus;
        this.description = description;
    }

    // Getters
    public int getApplicationStatusId() {
        return applicationStatusId;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setApplicationStatusId(int applicationStatusId) {
        this.applicationStatusId = applicationStatusId;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ApplicationStatus{" +
                "applicationStatusId=" + applicationStatusId +
                ", applicationStatus='" + applicationStatus + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}