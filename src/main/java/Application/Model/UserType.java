package Application.Model;

public class UserType {
    private int userTypeId;
    private String userType;

    // Default constructor
    public UserType() {}

    // Parameterized constructor
    public UserType(int userTypeId, String userType) {
        this.userTypeId = userTypeId;
        this.userType = userType;
    }

    // Getters
    public int getUserTypeId() {
        return userTypeId;
    }

    public String getUserType() {
        return userType;
    }

    // Setters
    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "userTypeId=" + userTypeId +
                ", userType='" + userType + '\'' +
                '}';
    }
}