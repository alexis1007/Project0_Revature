package Application.Service;

import java.time.LocalDate;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Application.DAO.UserDAO;
import Application.DAO.UserProfilesDAO;
import Application.Model.User;
import Application.Model.UserProfile;
import Application.Util.ValidationUtils;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserDAO userDAO;
    private final AuthService authService;
    private final UserProfilesDAO userProfilesDAO;

    public UserService() {
        userDAO = new UserDAO();
        this.authService = new AuthService(userDAO);
        this.userProfilesDAO = new UserProfilesDAO("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");
    }

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
        this.authService = new AuthService(userDAO);
        this.userProfilesDAO = new UserProfilesDAO("jdbc:postgresql://localhost:5432/postgres", "postgres", "password");
    }

    public User addUser(User user) {
        try {
            // Hash password and create user
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
            user.setPasswordHash(hashedPassword);
            User createdUser = userDAO.insertUser(user);

            if (createdUser != null) {
                // Create default profile with generic address
                UserProfile defaultProfile = new UserProfile();
                defaultProfile.setUserId(createdUser.getUserId());
                defaultProfile.setMailingAddressId(1); // Default address ID
                defaultProfile.setFirstName("New");
                defaultProfile.setLastName("User");
                defaultProfile.setPhoneNumber("000-000-0000");
                defaultProfile.setCreditScore(600);
                defaultProfile.setBirthDate(LocalDate.now().minusYears(18));

                userProfilesDAO.createUserProfile(defaultProfile);
                logger.info("Created default profile for user: {}", createdUser.getUsername());
            }

            return createdUser;
        } catch (Exception e) {
            logger.error("Error creating user with default profile", e);
            return null;
        }
    }

    public User updateUser(int userId, User user) {
        userDAO.updateUser(userId, user);
        return userDAO.getUserById(userId);
    }

    public User registerUser(String username, String password, int userTypeId) {
        return authService.registerUser(username, password, userTypeId);
    }

    public User loginUser(String username, String password) {
        return authService.authenticateUser(username, password);
    }

    public boolean changePassword(int userId, String currentPassword, String newPassword) {
        return authService.changePassword(userId, currentPassword, newPassword);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    public boolean isManager(int userId) {
        User user = getUserById(userId);
        return user != null && user.getUserTypeId() == 3; // 3 for MANAGER in database
    }

    public void deleteUser(int userId) {
        User user = getUserById(userId);
        if (user != null) {
            userDAO.deleteUser(userId);
            logger.info("User deleted: {}", userId);
        }
    }

    public void deactivateUser(int userId) {
        User user = getUserById(userId);
        if (user != null) {
            user.setActive(false);
            userDAO.updateUser(userId, user);
            logger.info("User deactivated: {}", userId);
        }
    }

    public boolean validatePassword(String password) {
        return ValidationUtils.isStrongPassword(password);
    }
}
