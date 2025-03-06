package Application.Service;

import Application.DAO.LoanTypeDAO;
import Application.Model.LoanType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoanTypeService {
    private static final Logger logger = LoggerFactory.getLogger(LoanTypeService.class);
    private final LoanTypeDAO loanTypeDAO;

    public LoanTypeService() {
        this.loanTypeDAO = new LoanTypeDAO();
    }

    public LoanTypeService(LoanTypeDAO loanTypeDAO) {
        this.loanTypeDAO = loanTypeDAO;
    }

    public List<LoanType> getAllLoanTypes() {
        return loanTypeDAO.getAllLoanTypes();
    }

    public LoanType getLoanTypeById(int id) {
        return loanTypeDAO.getLoanTypeById(id);
    }

    public LoanType createLoanType(LoanType loanType) {
        if (isValidLoanType(loanType)) {
            return loanTypeDAO.createLoanType(loanType);
        }
        return null;
    }

    private boolean isValidLoanType(LoanType loanType) {
        return loanType != null && 
               loanType.getLoanType() != null && 
               !loanType.getLoanType().trim().isEmpty();
    }
}