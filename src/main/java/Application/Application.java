package Application;

import Application.Controller.LoanTypeController;
import Application.Controller.LoansController;
import Application.Controller.MailingAddressController;
import Application.Controller.UserController;
import Application.Controller.UserProfilesController;
import io.javalin.Javalin;

public class Application {
    public static void main(String[] args) {
        // Remove AccountsController since we're using UserController
        UserController userController = new UserController();
        UserProfilesController userProfilesController = new UserProfilesController();
        LoansController loansController = new LoansController();
        MailingAddressController mailingAddressController = new MailingAddressController();
        LoanTypeController loanTypeController = new LoanTypeController();

        Javalin app = Javalin.create(config -> {
            config.plugins.enableCors(cors -> {
                cors.add(it -> it.anyHost());
            });
            config.plugins.enableDevLogging();
        }).start(7070);

        configureRoutes(app, userController, userProfilesController, 
                       loansController, mailingAddressController, loanTypeController);
    }

    private static void configureRoutes(Javalin app, 
                                      UserController userController,
                                      UserProfilesController userProfilesController,
                                      LoansController loansController,
                                      MailingAddressController mailingAddressController,
                                      LoanTypeController loanTypeController) {
        app.routes(() -> {
            // Authentication routes
            app.post("/auth/register", userController::createUserHandler);
            app.post("/auth/login", userController::loginHandler);

            // User routes
            app.get("/users/{user_id}", userController::getUserByIdHandler);
            app.get("/users", userController::getAllUsersHandler);
            app.put("/users/{user_id}", userController::updateUserHandler);

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

            // Loan Type routes
            app.get("/loan-types", loanTypeController::getAllLoanTypes);
            app.get("/loan-types/{id}", loanTypeController::getLoanTypeById);
            app.post("/loan-types", loanTypeController::createLoanType);

            // Mailing address routes
            app.get("/addresses", mailingAddressController::getAllAddressesHandler);
            app.get("/addresses/{id}", mailingAddressController::getAddressByIdHandler);
            app.post("/addresses", mailingAddressController::createAddressHandler);
            app.put("/addresses/{id}", mailingAddressController::updateAddressHandler);
            app.delete("/addresses/{id}", mailingAddressController::deleteAddressHandler);
        });
    }
}
