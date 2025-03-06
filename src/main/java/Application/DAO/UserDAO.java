package Application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Application.Model.User;
import Application.Util.ConnectionUtil;

public class UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    public List<User> getAllUsers() {
        Connection connection = ConnectionUtil.getConnection();
        List<User> users = new ArrayList<>();
        try {
            String sql = "SELECT * FROM loans.users";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                User user = new User(
                    rs.getInt("users_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("user_types_id")
                );
                users.add(user);
            }
        } catch(SQLException e) {
            logger.error("Error getting all users", e);
        }
        return users;
    }

    public User getUserById(int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM loans.users WHERE users_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return new User(
                    rs.getInt("users_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("user_types_id")
                );
            }
        } catch(SQLException e) {
            logger.error("Error getting user by ID", e);
        }
        return null;
    }

    public User insertUser(User user) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO loans.users (username, password, user_types_id, created_at, is_active) " +
                        "VALUES (?, ?, ?, CURRENT_TIMESTAMP, true)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getUserTypeId());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()) {
                int generatedUserId = (int) pkeyResultSet.getLong(1);
                return new User(generatedUserId, user.getUsername(), user.getPassword(), user.getUserTypeId());
            }
        } catch(SQLException e) {
            logger.error("Error inserting user", e);
        }
        return null;
    }

    public void updateUser(int userId, User user) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE loans.users SET username=?, password=?, user_types_id=? WHERE users_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getUserTypeId());
            preparedStatement.setInt(4, userId);

            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            logger.error("Error updating user", e);
        }
    }

    public User getUserByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM loans.users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return new User(
                    rs.getInt("users_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getInt("user_types_id")
                );
            }
        } catch(SQLException e) {
            logger.error("Error getting user by username", e);
        }
        return null;
    }

    public boolean authenticate(String username, String password) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM loans.users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch(SQLException e) {
            logger.error("Error during authentication", e);
        }
        return false;
    }
}
