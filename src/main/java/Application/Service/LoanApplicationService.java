package Application.Service;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Application.DAO.LoanApplicationsDAO;
import Application.Model.LoanApplication;

public class LoanApplicationService {
    private static final Logger logger = LoggerFactory.getLogger(LoanApplicationService.class);
    private final LoanApplicationsDAO loanApplicationsDAO;

    public LoanApplicationService() {
        this.loanApplicationsDAO = new LoanApplicationsDAO("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");
    }

    public LoanApplicationService(LoanApplicationsDAO loanApplicationsDAO) {
        this.loanApplicationsDAO = loanApplicationsDAO;
    }

    public List<LoanApplication> getAllLoans() {
        return loanApplicationsDAO.getAllLoans();
    }

    public List<LoanApplication> getLoansByUserId(int userId) {
        return loanApplicationsDAO.getLoansByUserId(userId);
    }

    public LoanApplication getLoanById(int loanId) {
        return loanApplicationsDAO.getLoanById(loanId);
    }

    public LoanApplication createLoan(LoanApplication loan) {
        if (isValidLoan(loan)) {
            // Calculate total balance based on principal, interest, and term
            BigDecimal totalBalance = calculateTotalBalance(
                loan.getPrincipalBalance(), 
                loan.getInterest(), 
                loan.getTermLength()
            );
            loan.setTotalBalance(totalBalance);
            
            return loanApplicationsDAO.createLoan(loan);
        }
        return null;
    }

    public void updateLoanStatus(int loanId, int statusId, int userId) {
        loanApplicationsDAO.updateLoanStatus(loanId, statusId, userId);
    }

    private boolean isValidLoan(LoanApplication loan) {
        return loan != null &&
               loan.getPrincipalBalance().compareTo(BigDecimal.ZERO) > 0 &&
               loan.getInterest().compareTo(BigDecimal.ZERO) >= 0 &&
               loan.getTermLength() > 0 &&
               loan.getBorrower() != null && !loan.getBorrower().trim().isEmpty();
    }

    private BigDecimal calculateTotalBalance(BigDecimal principal, BigDecimal interestRate, int termMonths) {
        // Simple interest calculation: P(1 + rt) where r is annual rate and t is years
        BigDecimal rate = interestRate.divide(new BigDecimal("100")); // Convert percentage to decimal
        BigDecimal years = new BigDecimal(termMonths).divide(new BigDecimal("12"));
        return principal.multiply(BigDecimal.ONE.add(rate.multiply(years)));
    }
}
