package Application;

import Application.Controller.AccountsController;
import Application.Controller.LoansController;
import Application.Controller.MailingAddressController;
import Application.Controller.UserProfilesController;
import io.javalin.Javalin;

public class Application {
    public static void main(String[] args) {

        AccountsController accountsController = new AccountsController();
        UserProfilesController userProfilesController = new UserProfilesController();
        LoansController loansController = new LoansController();
        MailingAddressController mailingAddressController = new MailingAddressController();

        Javalin app = Javalin.create(config -> {
                /*
            config.plugins.enableCors(cors -> {
                cors.add(it -> it.anyHost());
            });
            config.plugins.enableDevLogging(); 

            */
        }).start(7070);

        configureRoutes(app, accountsController, userProfilesController, 
                       loansController, mailingAddressController);
    }

    private static void configureRoutes(Javalin app, 
                                      AccountsController accountsController,
                                      UserProfilesController userProfilesController,
                                      LoansController loansController,
                                      MailingAddressController mailingAddressController) {
        // Group routes by domain
        app.routes(() -> {
            // Accounts routes
            app.post("/accounts", accountsController::createAccountHandler);
            app.get("/accounts/{account_id}", accountsController::getAccountByIdHandler);
            app.get("/accounts", accountsController::getAllAccountsHandler);
            app.put("/accounts/{account_id}", accountsController::updateAccountHandler);
            app.post("/accounts/login", accountsController::loginHandler);  // <- Change this line
        
            /*
            // User profiles routes
            app.post("/profiles", userProfilesController::createProfileHandler);
            app.get("/profiles/{profile_id}", userProfilesController::getProfileHandler);

            // Loans routes
            app.post("/loans", loansController::createLoanHandler);
            app.get("/loans/{loan_id}", loansController::getLoanHandler);

            // Mailing address routes
            app.post("/addresses", mailingAddressController::createAddressHandler);
            app.get("/addresses/{address_id}", mailingAddressController::getAddressHandler);

            */
        });
    }
}
