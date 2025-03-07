package Application;

import Application.Controller.LoanTypeController;
import Application.Controller.LoansController;
import Application.Controller.MailingAddressController;
import Application.Controller.UserController;
import Application.Controller.UserProfilesController;
import Application.Service.LoanApplicationService;
import Application.Service.UserService;
import io.javalin.Javalin;

public class Application {
    public static void main(String[] args) {
        UserService userService = new UserService();
        LoanApplicationService loanService = new LoanApplicationService();
        
        UserController userController = new UserController(userService);
        UserProfilesController userProfilesController = new UserProfilesController();
        LoansController loansController = new LoansController(loanService, userService);
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
            app.delete("/users/{user_id}", userController::deleteUserHandler);

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

            // User Profile routes
            app.get("/profiles", userProfilesController::getAllProfilesHandler);
            app.get("/profiles/{id}", userProfilesController::getProfileByIdHandler);
            app.post("/profiles", userProfilesController::createProfileHandler);
            app.put("/profiles/{id}", userProfilesController::updateProfileHandler);
            app.delete("/profiles/{id}", userProfilesController::deleteProfileHandler);

            // Regular User Loan routes
            app.get("/loans/user", loansController::getUserLoansHandler);          // Get user's own loans
            app.get("/loans/{id}", loansController::getLoanByIdHandler);          // View specific loan
            app.post("/loans", loansController::createLoanHandler);               // Create new loan
            app.put("/loans/{id}", loansController::updateLoanHandler);           // Edit loan if pending
            app.delete("/loans/{id}", loansController::deleteLoanHandler);        // Delete draft loan

            // Manager Loan routes
            app.get("/loans", loansController::getAllLoansHandler);               // View all loans
            app.put("/loans/{id}/approve", loansController::approveLoanHandler); // Approve loan
            app.put("/loans/{id}/reject", loansController::rejectLoanHandler);   // Reject loan
            app.put("/loans/{id}/review", loansController::reviewLoanHandler);   // Mark for review
        });
    }
}
