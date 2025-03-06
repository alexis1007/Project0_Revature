package Application.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import Application.Model.User;
import Application.Service.UserService;
import io.javalin.http.Context;

public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final ObjectMapper mapper;

    public UserController() {
        this.userService = new UserService();
        this.mapper = new ObjectMapper();
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
            
            User updatedUser = userService.updateUser(userId, user);
            if (updatedUser == null) {
                ctx.status(400);
            } else {
                ctx.json(mapper.writeValueAsString(updatedUser));
            }
        } catch (Exception e) {
            logger.error("Error updating user", e);
            ctx.status(500).json("Internal server error");
        }
    }

    public void loginHandler(Context ctx) {
        try {
            User credentials = mapper.readValue(ctx.body(), User.class);
            boolean isAuthenticated = userService.authenticate(
                credentials.getUsername(), 
                credentials.getPassword()
            );
            
            if (isAuthenticated) {
                User safeUser = userService.getUserByUsername(credentials.getUsername());
                safeUser.setPassword(null);
                
                ctx.sessionAttribute("user_id", safeUser.getId());
                ctx.status(200).json(safeUser);
            } else {
                ctx.status(401).json("Invalid credentials");
            }
        } catch (Exception e) {
            logger.error("Error during login", e);
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
