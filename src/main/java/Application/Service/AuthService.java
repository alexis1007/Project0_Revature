package Application.Service;

import java.time.LocalDateTime;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Application.DAO.UserDAO;
import Application.Model.User;

public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserDAO userDAO;

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User registerUser(String username, String rawPassword, int userTypeId) {
        try {
            if (userDAO.getUserByUsername(username) != null) {
                logger.warn("Registration failed: Username {} already exists", username);
                return null;
            }

            String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));
            User newUser = new User(username, hashedPassword, userTypeId);
            
            User createdUser = userDAO.insertUser(newUser);
            if (createdUser != null) {
                logger.info("User registered successfully: {}", username);
                return createdUser;
            }
        } catch (Exception e) {
            logger.error("Error during user registration", e);
        }
        return null;
    }

    public User authenticateUser(String username, String rawPassword) {
        try {
            User user = userDAO.getUserByUsername(username);
            if (user != null && BCrypt.checkpw(rawPassword, user.getPasswordHash())) {
                user.setLastLogin(LocalDateTime.now());
                userDAO.updateLastLogin(user.getUserId());
                logger.info("User authenticated successfully: {}", username);
                return user;
            }
            logger.warn("Authentication failed for username: {}", username);
        } catch (Exception e) {
            logger.error("Error during authentication", e);
        }
        return null;
    }

    public boolean changePassword(int userId, String currentPassword, String newPassword) {
        try {
            User user = userDAO.getUserById(userId);
            if (user != null && BCrypt.checkpw(currentPassword, user.getPasswordHash())) {
                String newHashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
                userDAO.updatePassword(userId, newHashedPassword);
                logger.info("Password changed successfully for user ID: {}", userId);
                return true;
            }
            logger.warn("Password change failed for user ID: {}", userId);
        } catch (Exception e) {
            logger.error("Error during password change", e);
        }
        return false;
    }
}