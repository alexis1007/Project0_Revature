package Application.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Application.DAO.UserDAO;
import Application.Model.User;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User addUser(User user) {
        return userDAO.insertUser(user);
    }

    public User updateUser(int userId, User user) {
        userDAO.updateUser(userId, user);
        return userDAO.getUserById(userId);
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
}
