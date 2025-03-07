package Application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Application.Model.User;
import Application.Util.ConnectionUtil;

public class UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT users_id, username, password_hash, user_types_id, " +
                        "created_at, last_login, is_active " +
                        "FROM loans.users";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
                User user = new User(
                    rs.getInt("users_id"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getInt("user_types_id"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("last_login") != null ? 
                        rs.getTimestamp("last_login").toLocalDateTime() : null,
                    rs.getBoolean("is_active")
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
            // Fixed SQL query - added WHERE clause
            String sql = "SELECT users_id, username, password_hash, user_types_id, " +
                        "created_at, last_login, is_active " +
                        "FROM loans.users WHERE users_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return new User(
                    rs.getInt("users_id"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getInt("user_types_id"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("last_login") != null ? 
                        rs.getTimestamp("last_login").toLocalDateTime() : null,
                    rs.getBoolean("is_active")
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
            String sql = "INSERT INTO loans.users (username, password_hash, user_types_id, created_at, is_active) " +
                        "VALUES (?, ?, ?, CURRENT_TIMESTAMP, true) RETURNING users_id, created_at";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPasswordHash());
            preparedStatement.setInt(3, user.getUserTypeId());

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return new User(
                    rs.getInt("users_id"),
                    user.getUsername(),
                    user.getPasswordHash(),
                    user.getUserTypeId(),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    null,
                    true
                );
            }
        } catch(SQLException e) {
            logger.error("Error inserting user: {}", e.getMessage());
        }
        return null;
    }

    public void updateUser(int userId, User user) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE loans.users SET username=?, password_hash=COALESCE(?, password_hash), " +
                        "user_types_id=?, is_active=? WHERE users_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setInt(3, user.getUserTypeId());
            ps.setBoolean(4, user.isActive());
            ps.setInt(5, userId);
            
            ps.executeUpdate();
        } catch(SQLException e) {
            logger.error("Error updating user", e);
            throw new RuntimeException("Error updating user", e);
        }
    }

    public User getUserByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            // Fixed SQL query to select specific columns and use password_hash
            String sql = "SELECT users_id, username, password_hash, user_types_id, " +
                        "created_at, last_login, is_active " +
                        "FROM loans.users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return new User(
                    rs.getInt("users_id"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getInt("user_types_id"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("last_login") != null ? 
                        rs.getTimestamp("last_login").toLocalDateTime() : null,
                    rs.getBoolean("is_active")
                );
            }
        } catch(SQLException e) {
            logger.error("Error getting user by username", e);
        }
        return null;
    }

    public boolean authenticate(String username, String hashedPassword) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            // Fixed SQL query to use password_hash
            String sql = "SELECT * FROM loans.users WHERE username = ? AND password_hash = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);

            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch(SQLException e) {
            logger.error("Error during authentication", e);
        }
        return false;
    }

    public void updateLastLogin(int userId) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE loans.users SET last_login = CURRENT_TIMESTAMP WHERE users_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch(SQLException e) {
            logger.error("Error updating last login: {}", e.getMessage());
        }
    }

    public void updatePassword(int userId, String hashedPassword) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE loans.users SET password_hash = ? WHERE users_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, hashedPassword);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch(SQLException e) {
            logger.error("Error updating password: {}", e.getMessage());
        }
    }

    public void deleteUser(int userId) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM loans.users WHERE users_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("User deleted successfully: {}", userId);
            } else {
                logger.warn("No user found to delete with ID: {}", userId);
            }
        } catch (SQLException e) {
            logger.error("Error deleting user: {}", e.getMessage());
            throw new RuntimeException("Error deleting user", e);
        }
    }
}
