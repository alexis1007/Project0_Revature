package Application.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import Application.Model.Account;
import Application.Service.AccountService;
import io.javalin.http.Context;

public class AccountsController {
    private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);
    private final AccountService accountService;
    private final ObjectMapper mapper;

    public AccountsController() {
        this.accountService = new AccountService();
        this.mapper = new ObjectMapper();
    }

    public void createAccountHandler(Context ctx) {
        try {
            Account account = mapper.readValue(ctx.body(), Account.class);
            
            // Validate request
            if (!isValidAccount(account)) {
                ctx.status(400).json("Invalid account data");
                return;
            }

            Account createdAccount = accountService.addAccount(account);
            if (createdAccount != null) {
                ctx.status(201).json(createdAccount);
            } else {
                ctx.status(400).json("Could not create account");
            }
        } catch (Exception e) {
            logger.error("Error creating account", e);
            ctx.status(500).json("Internal server error");
        }
    }

    public void getAccountByIdHandler(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        Account account = accountService.getAccountById(accountId);
        
        if (account == null) {
            ctx.status(404);
        } else {
            ctx.json(account);
        }
    }

    public void getAllAccountsHandler(Context ctx) {
        ctx.json(accountService.getAllAccounts());
    }


    public void updateAccountHandler(Context ctx) {
        try {
            Account account = mapper.readValue(ctx.body(), Account.class);
            int accountId = Integer.parseInt(ctx.pathParam("account_id"));
            
            Account updatedAccount = accountService.updateAccount(accountId, account);
            if (updatedAccount == null) {
                ctx.status(400);
            } else {
                ctx.json(mapper.writeValueAsString(updatedAccount));
            }
        } catch (Exception e) {
            logger.error("Error updating account", e);
            ctx.status(500).json("Internal server error");
        }

    }

    public void loginHandler(Context ctx) {
        try {
            Account credentials = mapper.readValue(ctx.body(), Account.class);
            boolean isAuthenticated = accountService.authenticate(
                credentials.getUsername(), 
                credentials.getPassword()
            );
            
            if (isAuthenticated) {
                // Crear objeto de respuesta sin datos sensibles
                Account safeAccount = accountService.getAccountByUsername(credentials.getUsername());
                safeAccount.setPassword(null); // <- Remove password from response
                
                // Establecer sesión y enviar respuesta
                ctx.sessionAttribute("user_id", safeAccount.getId());
                ctx.status(200).json(safeAccount);
            } else {
                ctx.status(401).json("Invalid credentials");
            }
        } catch (Exception e) {
            logger.error("Error during login", e);
            ctx.status(500).json("Internal server error");
        }
    }

    // Helper methods
    private boolean isValidAccount(Account account) {
        return account != null 
            && account.getUsername() != null 
            && !account.getUsername().trim().isEmpty()
            && account.getPassword() != null 
            && !account.getPassword().trim().isEmpty();
    }
}
