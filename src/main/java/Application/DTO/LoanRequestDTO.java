package Application.DTO;

import java.math.BigDecimal;

public class LoanRequestDTO {
    private Integer loanTypeId;
    private BigDecimal amount;
    private BigDecimal interest;
    private Integer termMonths;
    private String borrower;  // Nombre del solicitante del préstamo

    // Constructor básico
    public LoanRequestDTO() {}

    // Getters y setters
    public Integer getLoanTypeId() { return loanTypeId; }
    public void setLoanTypeId(Integer loanTypeId) { this.loanTypeId = loanTypeId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getInterest() { return interest; }
    public void setInterest(BigDecimal interest) { this.interest = interest; }

    public Integer getTermMonths() { return termMonths; }
    public void setTermMonths(Integer termMonths) { this.termMonths = termMonths; }

    public String getBorrower() { return borrower; }
    public void setBorrower(String borrower) { this.borrower = borrower; }

    public boolean isValid() {
        return loanTypeId != null && 
               amount != null && amount.compareTo(BigDecimal.ZERO) > 0 &&
               interest != null && interest.compareTo(BigDecimal.ZERO) >= 0 &&
               termMonths != null && termMonths > 0 &&
               borrower != null && !borrower.trim().isEmpty();
    }
}

