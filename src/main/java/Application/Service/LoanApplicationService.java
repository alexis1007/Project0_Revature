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
        this.loanApplicationsDAO = new LoanApplicationsDAO();
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

    public LoanApplication updateLoan(LoanApplication loan) {
        try {
            if (!isValidLoan(loan)) {
                logger.warn("Invalid loan data for update");
                return null;
            }

            // Recalculate total balance
            BigDecimal totalBalance = calculateTotalBalance(
                loan.getPrincipalBalance(), 
                loan.getInterest(), 
                loan.getTermLength()
            );
            loan.setTotalBalance(totalBalance);
            
            return loanApplicationsDAO.updateLoan(loan);
        } catch (Exception e) {
            logger.error("Error updating loan", e);
            throw new RuntimeException("Error updating loan", e);
        }
    }

    public void updateLoanStatus(int loanId, int statusId, int userId) {
        loanApplicationsDAO.updateLoanStatus(loanId, statusId, userId);
    }

    public void approveLoan(int loanId, int managerId) {
        try {
            // Status ID 4 es para APPROVED en la base de datos
            loanApplicationsDAO.updateLoanStatus(loanId, 4, managerId);
            logger.info("Loan {} approved by manager {}", loanId, managerId);
        } catch (Exception e) {
            logger.error("Error approving loan", e);
            throw new RuntimeException("Error approving loan", e);
        }
    }

    public void rejectLoan(int loanId, int managerId) {
        try {
            // Status ID 5 es para REJECTED en la base de datos
            loanApplicationsDAO.updateLoanStatus(loanId, 5, managerId);
            logger.info("Loan {} rejected by manager {}", loanId, managerId);
        } catch (Exception e) {
            logger.error("Error rejecting loan", e);
            throw new RuntimeException("Error rejecting loan", e);
        }
    }

    public void reviewLoan(int loanId, int managerId) {
        try {
            // Status ID 3 es para REVIEW en la base de datos
            loanApplicationsDAO.updateLoanStatus(loanId, 3, managerId);
            logger.info("Loan {} marked for review by manager {}", loanId, managerId);
        } catch (Exception e) {
            logger.error("Error marking loan for review", e);
            throw new RuntimeException("Error marking loan for review", e);
        }
    }

    public void deleteLoan(int loanId) {
        try {
            LoanApplication loan = getLoanById(loanId);
            if (loan != null) {
                loanApplicationsDAO.deleteLoanApplication(loanId);
                logger.info("Loan {} deleted successfully", loanId);
            } else {
                logger.warn("Attempted to delete non-existent loan: {}", loanId);
                throw new RuntimeException("Loan not found");
            }
        } catch (Exception e) {
            logger.error("Error deleting loan", e);
            throw new RuntimeException("Error deleting loan", e);
        }
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
