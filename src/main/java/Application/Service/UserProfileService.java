package Application.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Application.DAO.UserProfilesDAO;
import Application.Model.UserProfile;

public class UserProfileService {
    private static final Logger logger = LoggerFactory.getLogger(UserProfileService.class);
    private final UserProfilesDAO userProfilesDAO;

    public UserProfileService() {
        this.userProfilesDAO = new UserProfilesDAO("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
    }

    public UserProfileService(UserProfilesDAO userProfilesDAO) {
        this.userProfilesDAO = userProfilesDAO;
    }

    public List<UserProfile> getAllUserProfiles() {
        return userProfilesDAO.getAllUserProfiles();
    }

    public UserProfile getUserProfileById(int id) {
        return userProfilesDAO.getUserProfileById(id);
    }

    public UserProfile createUserProfile(UserProfile profile) {
        if (isValidProfile(profile)) {
            return userProfilesDAO.createUserProfile(profile);
        }
        return null;
    }

    public void updateUserProfile(int profileId, UserProfile profile) {
        if (isValidProfile(profile)) {
            userProfilesDAO.updateUserProfile(profileId, profile);
        }
    }

    public void deleteUserProfile(int profileId) {
        userProfilesDAO.deleteUserProfile(profileId);
    }

    public UserProfile getUserProfileByUserId(int userId) {
        return userProfilesDAO.getUserProfileByUserId(userId);
    }

    private boolean isValidProfile(UserProfile profile) {
        if (profile == null) return false;

        // Check required fields
        if (profile.getFirstName() == null || profile.getFirstName().trim().isEmpty() ||
            profile.getLastName() == null || profile.getLastName().trim().isEmpty()) {
            return false;
        }

        // Validate credit score
        if (profile.getCreditScore() < 300 || profile.getCreditScore() > 850) {
            return false;
        }

        // Validate age (must be at least 18)
        if (profile.getBirthDate() != null) {
            LocalDate now = LocalDate.now();
            Period age = Period.between(profile.getBirthDate(), now);
            if (age.getYears() < 18) {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }
}
