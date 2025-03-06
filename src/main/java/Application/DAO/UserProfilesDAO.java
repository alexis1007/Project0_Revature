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

import Application.Model.UserProfile;
import Application.Util.ConnectionUtil;

public class UserProfilesDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserProfilesDAO.class);

    private final String url;
    private final String dbUser;
    private final String dbPassword;

    public UserProfilesDAO(String url, String dbUser, String dbPassword) {
        this.url = url;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public List<UserProfile> getAllUserProfiles() {
        List<UserProfile> profiles = new ArrayList<>();
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM loans.user_profiles";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                profiles.add(new UserProfile(
                    rs.getInt("user_profiles_id"),
                    rs.getInt("users_id"),
                    rs.getInt("mailing_addresses_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("phone_number"),
                    rs.getInt("credit_score"),
                    rs.getDate("birth_date").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            logger.error("Error getting all user profiles", e);
        }
        return profiles;
    }

    public UserProfile getUserProfileById(int id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM loans.user_profiles WHERE user_profiles_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new UserProfile(
                    rs.getInt("user_profiles_id"),
                    rs.getInt("users_id"),
                    rs.getInt("mailing_addresses_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("phone_number"),
                    rs.getInt("credit_score"),
                    rs.getDate("birth_date").toLocalDate()
                );
            }
        } catch (SQLException e) {
            logger.error("Error getting user profile by ID", e);
        }
        return null;
    }

    public UserProfile createUserProfile(UserProfile profile) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO loans.user_profiles (users_id, mailing_addresses_id, first_name, " +
                        "last_name, phone_number, credit_score, birth_date) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING user_profiles_id";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, profile.getUserId());
            ps.setInt(2, profile.getMailingAddressId());
            ps.setString(3, profile.getFirstName());
            ps.setString(4, profile.getLastName());
            ps.setString(5, profile.getPhoneNumber());
            ps.setInt(6, profile.getCreditScore());
            ps.setObject(7, profile.getBirthDate());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                profile.setUserProfileId(rs.getInt(1));
                return profile;
            }
        } catch (SQLException e) {
            logger.error("Error creating user profile", e);
        }
        return null;
    }

    public void updateUserProfile(int profileId, UserProfile profile) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE loans.user_profiles SET users_id=?, mailing_addresses_id=?, " +
                        "first_name=?, last_name=?, phone_number=?, credit_score=?, birth_date=? " +
                        "WHERE user_profiles_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, profile.getUserId());
            ps.setInt(2, profile.getMailingAddressId());
            ps.setString(3, profile.getFirstName());
            ps.setString(4, profile.getLastName());
            ps.setString(5, profile.getPhoneNumber());
            ps.setInt(6, profile.getCreditScore());
            ps.setObject(7, profile.getBirthDate());
            ps.setInt(8, profileId);

            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error updating user profile", e);
        }
    }

    public void deleteUserProfile(int profileId) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM loans.user_profiles WHERE user_profiles_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, profileId);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting user profile", e);
        }
    }

    public UserProfile getUserProfileByUserId(int userId) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM loans.user_profiles WHERE users_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new UserProfile(
                    rs.getInt("user_profiles_id"),
                    rs.getInt("users_id"),
                    rs.getInt("mailing_addresses_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("phone_number"),
                    rs.getInt("credit_score"),
                    rs.getDate("birth_date").toLocalDate()
                );
            }
        } catch (SQLException e) {
            logger.error("Error getting user profile by user ID", e);
        }
        return null;
    }
}
