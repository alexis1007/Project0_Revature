package Application.Controller;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Application.DTO.LoanRequestDTO;
import Application.DTO.LoanResponseDTO;
import Application.Model.LoanApplication;
import Application.Service.LoanApplicationService;
import Application.Service.UserService;
import Application.Util.AuthUtils;
import io.javalin.http.Context;

public class LoansController {
    private static final Logger logger = LoggerFactory.getLogger(LoansController.class);
    private final LoanApplicationService loanService;
    private final UserService userService;

    public LoansController(LoanApplicationService loanService, UserService userService) {
        this.loanService = loanService;
        this.userService = userService;
    }

    public void getAllLoansHandler(Context ctx) {
        if (!AuthUtils.isAuthenticated(ctx)) {
            return;
        }

        Integer userId = ctx.sessionAttribute("user_id");
        if (AuthUtils.checkManager(ctx, userService)) {
            ctx.json(loanService.getAllLoans());
        } else {
            ctx.json(loanService.getLoansByUserId(userId));
        }
    }

    public void getLoanByIdHandler(Context ctx) {
        try {
            if (!AuthUtils.isAuthenticated(ctx)) {
                return;
            }

            int loanId = Integer.parseInt(ctx.pathParam("id"));
            LoanApplication loan = loanService.getLoanById(loanId);
            
            if (loan != null && AuthUtils.checkOwnerOrManager(ctx, userService, loan.getCreatedBy())) {
                ctx.json(LoanResponseDTO.fromLoanApplication(loan));
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
            if (!AuthUtils.isAuthenticated(ctx)) {
                return;
            }

            Integer userId = ctx.sessionAttribute("user_id");
            LoanRequestDTO request = ctx.bodyAsClass(LoanRequestDTO.class);
            
            // Convertir DTO a modelo
            LoanApplication loan = new LoanApplication();
            loan.setLoanTypeId(request.getLoanTypeId());
            loan.setPrincipalBalance(request.getAmount());
            loan.setInterest(request.getInterest());
            loan.setTermLength(request.getTermMonths());
            loan.setBorrower(request.getBorrower()); 
            loan.setCreatedBy(userId);
            
            LoanApplication created = loanService.createLoan(loan);
            if (created != null) {
                ctx.status(201).json(LoanResponseDTO.fromLoanApplication(created));
            } else {
                ctx.status(400).json("Invalid loan data");
            }
        } catch (Exception e) {
            logger.error("Error creating loan", e);
            ctx.status(500).json("Internal server error");
        }
    }

    public void updateLoanHandler(Context ctx) {
        try {
            if (!AuthUtils.isAuthenticated(ctx)) {
                return;
            }

            int loanId = Integer.parseInt(ctx.pathParam("id"));
            LoanApplication loan = loanService.getLoanById(loanId);

            if (loan == null || !AuthUtils.checkOwnerOrManager(ctx, userService, loan.getCreatedBy())) {
                ctx.status(403).json("Not authorized to edit this loan");
                return;
            }

            LoanRequestDTO request = ctx.bodyAsClass(LoanRequestDTO.class);
            loan.setLoanTypeId(request.getLoanTypeId());
            loan.setPrincipalBalance(request.getAmount());
            loan.setInterest(request.getInterest());
            loan.setTermLength(request.getTermMonths());
            loan.setBorrower(request.getBorrower());  // Cambiado de setPurpose a setBorrower

            LoanApplication updated = loanService.updateLoan(loan);
            if (updated != null) {
                ctx.json(LoanResponseDTO.fromLoanApplication(updated));
            } else {
                ctx.status(400).json("Invalid loan data");
            }
        } catch (Exception e) {
            logger.error("Error updating loan", e);
            ctx.status(500).json("Internal server error");
        }
    }

    public void updateLoanStatusHandler(Context ctx) {
        int loanId = Integer.parseInt(ctx.pathParam("id"));
        int statusId = Integer.parseInt(ctx.formParam("status_id"));
        
        handleManagerOperation(ctx, loanId, "update status",
            id -> loanService.updateLoanStatus(id, statusId, ctx.sessionAttribute("user_id")));
    }

    private void handleManagerOperation(Context ctx, int loanId, String operation, 
        Consumer<Integer> action) {
        try {
            if (!AuthUtils.isAuthenticated(ctx) || !AuthUtils.checkManager(ctx, userService)) {
                return;
            }

            action.accept(loanId);
            ctx.status(200).json("Loan " + operation + " successfully");
        } catch (Exception e) {
            logger.error("Error {} loan: {}", operation, e.getMessage());
            ctx.status(500).json("Internal server error");
        }
    }

    public void approveLoanHandler(Context ctx) {
        int loanId = Integer.parseInt(ctx.pathParam("id"));
        handleManagerOperation(ctx, loanId, "approve", 
            id -> loanService.approveLoan(id, ctx.sessionAttribute("user_id")));
    }

    public void rejectLoanHandler(Context ctx) {
        int loanId = Integer.parseInt(ctx.pathParam("id"));
        handleManagerOperation(ctx, loanId, "reject", 
            id -> loanService.rejectLoan(id, ctx.sessionAttribute("user_id")));
    }

    public void reviewLoanHandler(Context ctx) {
        int loanId = Integer.parseInt(ctx.pathParam("id"));
        handleManagerOperation(ctx, loanId, "review", 
            id -> loanService.reviewLoan(id, ctx.sessionAttribute("user_id")));
    }

    public void deleteLoanHandler(Context ctx) {
        try {
            if (!AuthUtils.isAuthenticated(ctx)) {
                return;
            }

            int loanId = Integer.parseInt(ctx.pathParam("id"));
            LoanApplication loan = loanService.getLoanById(loanId);

            if (loan == null) {
                ctx.status(404).json("Loan not found");
                return;
            }

            if (!AuthUtils.checkOwnerOrManager(ctx, userService, loan.getCreatedBy())) {
                ctx.status(403).json("Not authorized to delete this loan");
                return;
            }

            loanService.deleteLoan(loanId);
            ctx.status(204);
        } catch (Exception e) {
            logger.error("Error deleting loan: {}", e.getMessage());
            ctx.status(500).json("Internal server error");
        }
    }

    public void getUserLoansHandler(Context ctx) {
        try {
            if (!AuthUtils.isAuthenticated(ctx)) {
                return;
            }

            Integer userId = ctx.sessionAttribute("user_id");
            List<LoanApplication> userLoans = loanService.getLoansByUserId(userId);
            
            // Convert to DTOs
            List<LoanResponseDTO> loanDTOs = userLoans.stream()
                .map(LoanResponseDTO::fromLoanApplication)
                .collect(Collectors.toList());

            ctx.json(loanDTOs);
        } catch (Exception e) {
            logger.error("Error getting user loans: {}", e.getMessage());
            ctx.status(500).json("Internal server error");
        }
    }
}
