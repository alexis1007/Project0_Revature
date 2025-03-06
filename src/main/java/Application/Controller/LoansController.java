package Application.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Application.Model.LoanApplication;
import Application.Service.LoanApplicationService;
import io.javalin.http.Context;

public class LoansController {
    private static final Logger logger = LoggerFactory.getLogger(LoansController.class);
    private final LoanApplicationService loanService;

    public LoansController() {
        this.loanService = new LoanApplicationService();
    }

    public void getAllLoansHandler(Context ctx) {
        Integer userId = ctx.sessionAttribute("user_id");
        if (userId == null) {
            ctx.status(401).json("Unauthorized");
            return;
        }

        // If user is admin/manager, return all loans, otherwise return only their loans
        if (isManager(userId)) {
            ctx.json(loanService.getAllLoans());
        } else {
            ctx.json(loanService.getLoansByUserId(userId));
        }
    }

    public void getLoanByIdHandler(Context ctx) {
        try {
            Integer userId = ctx.sessionAttribute("user_id");
            if (userId == null) {
                ctx.status(401).json("Unauthorized");
                return;
            }

            int loanId = Integer.parseInt(ctx.pathParam("id"));
            LoanApplication loan = loanService.getLoanById(loanId);
            
            if (loan != null) {
                // Check if user is authorized to view this loan
                if (loan.getCreatedBy() == userId || isManager(userId)) {
                    ctx.json(loan);
                } else {
                    ctx.status(403).json("Forbidden");
                }
            } else {
                ctx.status(404).json("Loan not found");
            }
        } catch (Exception e) {
            logger.error("Error getting loan", e);
            ctx.status(500).json("Internal server error");
        }
    }

    public void createLoanHandler(Context ctx) {
        try {
            Integer userId = ctx.sessionAttribute("user_id");
            if (userId == null) {
                ctx.status(401).json("Unauthorized");
                return;
            }

            LoanApplication loan = ctx.bodyAsClass(LoanApplication.class);
            loan.setCreatedBy(userId);
            
            LoanApplication created = loanService.createLoan(loan);
            if (created != null) {
                ctx.status(201).json(created);
            } else {
                ctx.status(400).json("Invalid loan data");
            }
        } catch (Exception e) {
            logger.error("Error creating loan", e);
            ctx.status(500).json("Internal server error");
        }
    }

    public void updateLoanStatusHandler(Context ctx) {
        try {
            Integer userId = ctx.sessionAttribute("user_id");
            if (userId == null) {
                ctx.status(401).json("Unauthorized");
                return;
            }

            if (!isManager(userId)) {
                ctx.status(403).json("Forbidden: Only managers can update loan status");
                return;
            }

            int loanId = Integer.parseInt(ctx.pathParam("id"));
            int statusId = Integer.parseInt(ctx.formParam("status_id"));
            
            loanService.updateLoanStatus(loanId, statusId, userId);
            ctx.status(204);
        } catch (Exception e) {
            logger.error("Error updating loan status", e);
            ctx.status(500).json("Internal server error");
        }
    }

    private boolean isManager(int userId) {
        // Implement manager check logic here
        // You might want to check the user_types_id in the users table
        return false; // Temporary implementation
    }
}
