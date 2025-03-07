package Application.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LoanApplicationDTO {
    private Integer id;
    private Integer loanTypeId;
    private BigDecimal amount;
    private BigDecimal interest;
    private Integer termMonths;
    private String purpose;
    private String status;
    private LocalDateTime applicationDate;
    private Integer applicantId;
    private String applicantName;

    // Constructors
    public LoanApplicationDTO() {}

    public LoanApplicationDTO(Integer id, Integer loanTypeId, BigDecimal amount, BigDecimal interest,
                            Integer termMonths, String purpose, String status, LocalDateTime applicationDate,
                            Integer applicantId, String applicantName) {
        this.id = id;
        this.loanTypeId = loanTypeId;
        this.amount = amount;
        this.interest = interest;
        this.termMonths = termMonths;
        this.purpose = purpose;
        this.status = status;
        this.applicationDate = applicationDate;
        this.applicantId = applicantId;
        this.applicantName = applicantName;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getLoanTypeId() { return loanTypeId; }
    public void setLoanTypeId(Integer loanTypeId) { this.loanTypeId = loanTypeId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getInterest() { return interest; }
    public void setInterest(BigDecimal interest) { this.interest = interest; }

    public Integer getTermMonths() { return termMonths; }
    public void setTermMonths(Integer termMonths) { this.termMonths = termMonths; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDateTime applicationDate) { this.applicationDate = applicationDate; }

    public Integer getApplicantId() { return applicantId; }
    public void setApplicantId(Integer applicantId) { this.applicantId = applicantId; }

    public String getApplicantName() { return applicantName; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }
}