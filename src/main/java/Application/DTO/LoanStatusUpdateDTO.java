package Application.DTO;

public class LoanStatusUpdateDTO {
    private String status;
    private String comments;

    public LoanStatusUpdateDTO() {}

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
}