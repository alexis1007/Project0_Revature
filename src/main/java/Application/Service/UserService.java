package Application.Service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Application.DAO.UserDAO;
import Application.Model.User;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserDAO userDAO;
    private final AuthService authService;

    public UserService() {
        userDAO = new UserDAO();
        this.authService = new AuthService(userDAO);
    }

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
        this.authService = new AuthService(userDAO);
    }

    public User addUser(User user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPasswordHash(hashedPassword);
        return userDAO.insertUser(user);
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

    public boolean authenticate(String username, String password) {
        return userDAO.authenticate(username, password);
    }

    public boolean isManager(int userId) {
        User user = getUserById(userId);
        return user != null && user.getUserTypeId() == 3; // 3 for MANAGER in database
    }
}
