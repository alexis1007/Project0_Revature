package Application.DAO;

import Application.Model.MailingAddress;
import Application.Model.UserProfile;

import java.util.List;

public class UserProfilesDAO {
    private final String url;
    private final String dbUser;
    private final String dbPassword;

    public UserProfilesDAO(String url, String dbUser, String dbPassword) {
        this.url = url;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }
    public UserProfile createUserProfile(UserProfile userProfile){
        return null;
    }
    public List<UserProfile> getAllUsersProfiles(){
        return null;
    }
    public void updateUserProfile(UserProfile userProfile){

    }
    public void deleteUserProfile(UserProfile userProfile){

    }
}
