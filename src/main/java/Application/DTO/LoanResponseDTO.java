package Application.DTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import Application.Model.LoanApplication;
import Application.Util.ValidationUtils;

public class LoanResponseDTO {
    private Integer id;
    private String loanType;
    private BigDecimal amount;
    private BigDecimal interest;
    private Integer termMonths;
    private String status;
    private LocalDateTime applicationDate;
    private BigDecimal monthlyPayment;
    private String borrower;  // Agregar este campo
    private BigDecimal totalInterest; // Este campo se usa en calculateTotalInterest pero no está declarado correctamente

    public LoanResponseDTO() {}

    public static LoanResponseDTO fromLoanApplication(LoanApplication loan) {
        LoanResponseDTO dto = new LoanResponseDTO();
        dto.setId(loan.getLoanApplicationId());  
        dto.setLoanType(String.valueOf(loan.getLoanTypeId())); 
        dto.setAmount(loan.getPrincipalBalance());
        dto.setInterest(loan.getInterest());
        dto.setTermMonths(loan.getTermLength());
        dto.setStatus(getStatusString(loan.getApplicationStatusId())); 
        dto.setApplicationDate(loan.getApplicationDate());
        dto.setBorrower(loan.getBorrower());
        dto.calculateMonthlyPayment();
        dto.calculateTotalInterest();
        return dto;
    }

    // Helper method to convert status ID to string
    private static String getStatusString(int statusId) {
        switch(statusId) {
            case 1: return "DRAFT";
            case 2: return "PENDING";
            case 3: return "REVIEW";
            case 4: return "APPROVED";
            case 5: return "REJECTED";
            case 6: return "CANCELLED";
            default: return "UNKNOWN";
        }
    }

    private void calculateMonthlyPayment() {
        if (ValidationUtils.isValidLoanAmount(amount) && 
            ValidationUtils.isValidInterestRate(interest) && 
            termMonths > 0) {
            
            // División con escala y modo de redondeo
            BigDecimal monthlyRate = interest.divide(BigDecimal.valueOf(1200), 10, RoundingMode.HALF_UP);
            
            // Calcular (1 + r)^n
            BigDecimal factor = BigDecimal.ONE.add(monthlyRate).pow(termMonths);
            
            // Calcular P * r * (1 + r)^n / ((1 + r)^n - 1)
            this.monthlyPayment = amount
                .multiply(monthlyRate)
                .multiply(factor)
                .divide(factor.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
        }
    }

    private void calculateTotalInterest() {
        if (monthlyPayment != null && amount != null && termMonths != null) {
            this.totalInterest = monthlyPayment.multiply(BigDecimal.valueOf(termMonths))
                .subtract(amount)
                .setScale(2, RoundingMode.HALF_UP);
        }
    }

    // Getters
    public Integer getId() {
        return id;
    }

    public String getLoanType() {
        return loanType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public Integer getTermMonths() {
        return termMonths;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public String getBorrower() {
        return borrower;
    }

    public BigDecimal getTotalInterest() {
        return totalInterest;
    }

    // Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public void setTermMonths(Integer termMonths) {
        this.termMonths = termMonths;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public void setMonthlyPayment(BigDecimal monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    @Override
    public String toString() {
        return "LoanResponseDTO{" +
            "id=" + id +
            ", loanType='" + loanType + '\'' +
            ", amount=" + amount +
            ", interest=" + interest +
            ", termMonths=" + termMonths +
            ", status='" + status + '\'' +
            ", applicationDate=" + applicationDate +
            ", monthlyPayment=" + monthlyPayment +
            ", borrower='" + borrower + '\'' +
            ", totalInterest=" + totalInterest +
            '}';
    }
}