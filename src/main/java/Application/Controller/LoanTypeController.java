package Application.Controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Application.Model.LoanType;
import Application.Service.LoanTypeService;
import io.javalin.http.Context;

public class LoanTypeController {
    private static final Logger logger = LoggerFactory.getLogger(LoanTypeController.class);
    private final LoanTypeService loanTypeService;

    public LoanTypeController() {
        this.loanTypeService = new LoanTypeService();
    }

    public void getAllLoanTypes(Context ctx) {
        ctx.json(loanTypeService.getAllLoanTypes());
    }

    public void getLoanTypeById(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("id"));
        LoanType loanType = loanTypeService.getLoanTypeById(id);
        if (loanType != null) {
            ctx.json(loanType);
        } else {
            ctx.status(404).json("Loan type not found");
        }
    }

    public void createLoanType(Context ctx) {
        try {
            LoanType loanType = ctx.bodyAsClass(LoanType.class);
            LoanType created = loanTypeService.createLoanType(loanType);
            
            if (created != null) {
                ctx.status(201).json(created);
            } else {
                ctx.status(400).json("Invalid loan type data");
            }
        } catch (Exception e) {
            logger.error("Error creating loan type", e);
            ctx.status(500).json("Internal server error");
        }
    }
}