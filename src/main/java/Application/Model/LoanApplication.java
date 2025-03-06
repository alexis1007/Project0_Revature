package Application.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LoanApplication {
    private int loanApplicationId;
    private int loanTypeId;
    private int applicationStatusId;
    private int userProfileId;
    private BigDecimal principalBalance;
    private BigDecimal interest;
    private int termLength;
    private BigDecimal totalBalance;
    private String borrower;
    private LocalDateTime applicationDate;
    private int createdBy;
    private LocalDateTime createdAt;
    private Integer updatedBy;
    private LocalDateTime updatedAt;
    private Integer statusChangedBy;
    private LocalDateTime statusChangedAt;

    // Default constructor
    public LoanApplication() {}

    // Full constructor
    public LoanApplication(int loanApplicationId, int loanTypeId, int applicationStatusId,
                         int userProfileId, BigDecimal principalBalance, BigDecimal interest,
                         int termLength, BigDecimal totalBalance, String borrower,
                         LocalDateTime applicationDate, int createdBy, LocalDateTime createdAt,
                         Integer updatedBy, LocalDateTime updatedAt,
                         Integer statusChangedBy, LocalDateTime statusChangedAt) {
        this.loanApplicationId = loanApplicationId;
        this.loanTypeId = loanTypeId;
        this.applicationStatusId = applicationStatusId;
        this.userProfileId = userProfileId;
        this.principalBalance = principalBalance;
        this.interest = interest;
        this.termLength = termLength;
        this.totalBalance = totalBalance;
        this.borrower = borrower;
        this.applicationDate = applicationDate;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.statusChangedBy = statusChangedBy;
        this.statusChangedAt = statusChangedAt;
    }

    // Getters
    public int getLoanApplicationId() { return loanApplicationId; }
    public int getLoanTypeId() { return loanTypeId; }
    public int getApplicationStatusId() { return applicationStatusId; }
    public int getUserProfileId() { return userProfileId; }
    public BigDecimal getPrincipalBalance() { return principalBalance; }
    public BigDecimal getInterest() { return interest; }
    public int getTermLength() { return termLength; }
    public BigDecimal getTotalBalance() { return totalBalance; }
    public String getBorrower() { return borrower; }
    public LocalDateTime getApplicationDate() { return applicationDate; }
    public int getCreatedBy() { return createdBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Integer getUpdatedBy() { return updatedBy; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public Integer getStatusChangedBy() { return statusChangedBy; }
    public LocalDateTime getStatusChangedAt() { return statusChangedAt; }

    // Setters
    public void setLoanApplicationId(int loanApplicationId) { this.loanApplicationId = loanApplicationId; }
    public void setLoanTypeId(int loanTypeId) { this.loanTypeId = loanTypeId; }
    public void setApplicationStatusId(int applicationStatusId) { this.applicationStatusId = applicationStatusId; }
    public void setUserProfileId(int userProfileId) { this.userProfileId = userProfileId; }
    public void setPrincipalBalance(BigDecimal principalBalance) { this.principalBalance = principalBalance; }
    public void setInterest(BigDecimal interest) { this.interest = interest; }
    public void setTermLength(int termLength) { this.termLength = termLength; }
    public void setTotalBalance(BigDecimal totalBalance) { this.totalBalance = totalBalance; }
    public void setBorrower(String borrower) { this.borrower = borrower; }
    public void setApplicationDate(LocalDateTime applicationDate) { this.applicationDate = applicationDate; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedBy(Integer updatedBy) { this.updatedBy = updatedBy; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public void setStatusChangedBy(Integer statusChangedBy) { this.statusChangedBy = statusChangedBy; }
    public void setStatusChangedAt(LocalDateTime statusChangedAt) { this.statusChangedAt = statusChangedAt; }

    @Override
    public String toString() {
        return "LoanApplication{" +
                "loanApplicationId=" + loanApplicationId +
                ", loanTypeId=" + loanTypeId +
                ", applicationStatusId=" + applicationStatusId +
                ", userProfileId=" + userProfileId +
                ", principalBalance=" + principalBalance +
                ", interest=" + interest +
                ", termLength=" + termLength +
                ", totalBalance=" + totalBalance +
                ", borrower='" + borrower + '\'' +
                ", applicationDate=" + applicationDate +
                ", createdBy=" + createdBy +
                ", createdAt=" + createdAt +
                ", updatedBy=" + updatedBy +
                ", updatedAt=" + updatedAt +
                ", statusChangedBy=" + statusChangedBy +
                ", statusChangedAt=" + statusChangedAt +
                '}';
    }
}