package Application.Controller;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import Application.DTO.LoginRequest;
import Application.DTO.LoginResponse;
import Application.Model.User;
import Application.Service.UserService;
import io.javalin.http.Context;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final ObjectMapper mapper;

    public UserController(UserService userService) {
        this.userService = userService;
        this.mapper = new ObjectMapper();
        mapper.findAndRegisterModules(); // Timestamps are supposed to improve
    }

    public void createUserHandler(Context ctx) {
        try {
            User user = mapper.readValue(ctx.body(), User.class);
            
            if (!isValidUser(user)) {
                ctx.status(400).json("Invalid user data");
                return;
            }

            User createdUser = userService.addUser(user);
            if (createdUser != null) {
                ctx.status(201).json(createdUser);
            } else {
                ctx.status(400).json("Could not create user");
            }
        } catch (Exception e) {
            logger.error("Error creating user", e);
            ctx.status(500).json("Internal server error");
        }
    }

    public void getUserByIdHandler(Context ctx) {
        int userId = Integer.parseInt(ctx.pathParam("user_id"));
        User user = userService.getUserById(userId);
        
        if (user == null) {
            ctx.status(404);
        } else {
            ctx.json(user);
        }
    }

    public void getAllUsersHandler(Context ctx) {
        ctx.json(userService.getAllUsers());
    }

    public void updateUserHandler(Context ctx) {
        try {
            User user = mapper.readValue(ctx.body(), User.class);
            int userId = Integer.parseInt(ctx.pathParam("user_id"));
            
            // Hash the new password if provided
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
                user.setPasswordHash(hashedPassword);
            }
            
            User updatedUser = userService.updateUser(userId, user);
            if (updatedUser == null) {
                ctx.status(400).json("Could not update user");
            } else {
                ctx.json(updatedUser);
            }
        } catch (Exception e) {
            logger.error("Error updating user", e);
            ctx.status(500).json("Internal server error");
        }
    }

    public void loginHandler(Context ctx) {
        try {
            LoginRequest loginRequest = ctx.bodyAsClass(LoginRequest.class);
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            System.out.println(username);
            
            
            User user = userService.loginUser(username, password);
            if (user != null) {
                ctx.sessionAttribute("user_id", user.getUserId());
                ctx.json(new LoginResponse(
                    user.getUserId(), 
                    user.getUsername(), 
                    userService.isManager(user.getUserId())
                ));
            } else {
                ctx.status(401).json("Invalid credentials");
            }
        } catch (Exception e) {
            logger.error("Error in login handler", e);
            ctx.status(500).json("Internal server error");
        }
    }

    public void logoutHandler(Context ctx) {
        try {
            ctx.sessionAttribute("user_id", null);
            ctx.sessionAttribute("is_manager", null);
            ctx.json("Logged out successfully");
        } catch (Exception e) {
            logger.error("Error during logout", e);
            ctx.status(500).json("Internal server error");
        }
    }

    public void deleteUserHandler(Context ctx) {
        try {
            Integer currentUserId = ctx.sessionAttribute("user_id");
            if (currentUserId == null) {
                ctx.status(401).json("Unauthorized");
                return;
            }

            int userIdToDelete = Integer.parseInt(ctx.pathParam("user_id"));
            
            // Only allow managers or self-deletion
            if (!userService.isManager(currentUserId) && currentUserId != userIdToDelete) {
                ctx.status(403).json("Forbidden: Can only delete own account or requires manager privileges");
                return;
            }

            userService.deleteUser(userIdToDelete);
            ctx.status(204).json("User deleted successfully");
        } catch (Exception e) {
            logger.error("Error deleting user", e);
            ctx.status(500).json("Internal server error");
        }
    }

    private boolean isValidUser(User user) {
        return user != null 
            && user.getUsername() != null 
            && !user.getUsername().trim().isEmpty()
            && user.getPassword() != null 
            && !user.getPassword().trim().isEmpty();
    }
}
