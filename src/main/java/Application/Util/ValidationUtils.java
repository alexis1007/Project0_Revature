package Application.Util;

import java.math.BigDecimal;

import Application.Model.LoanApplication;

public class ValidationUtils {
    public static boolean isValidLoanAmount(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isValidInterestRate(BigDecimal rate) {
        return rate != null && 
               rate.compareTo(BigDecimal.ZERO) >= 0 && 
               rate.compareTo(new BigDecimal("100")) <= 0;
    }

    public static boolean isStrongPassword(String password) {
        return password != null && 
               password.length() >= 8 && 
               password.matches(".*[A-Z].*") && 
               password.matches(".*[a-z].*") && 
               password.matches(".*[0-9].*");
    }
    
    public static ValidationResult validateLoan(LoanApplication loan) {
        ValidationResult result = new ValidationResult();
        
        if (loan == null) {
            result.addError("Loan cannot be null");
            return result;
        }

        if (!isValidLoanAmount(loan.getPrincipalBalance())) {
            result.addError("Principal balance must be greater than zero");
        }

        if (!isValidInterestRate(loan.getInterest())) {
            result.addError("Interest rate must be between 0 and 100");
        }

        if (loan.getTermLength() <= 0) {
            result.addError("Term length must be greater than zero");
        }

        if (loan.getBorrower() == null || loan.getBorrower().trim().isEmpty()) {
            result.addError("Borrower name is required");
        }

        return result;
    }
}