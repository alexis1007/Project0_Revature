package Application.Model;

import java.time.LocalDate;

public class UserProfile {
    private int userProfileId;
    private int userId;
    private int mailingAddressId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int creditScore;
    private LocalDate birthDate;

    // Default constructor
    public UserProfile() {}

    // Full constructor
    public UserProfile(int userProfileId, int userId, int mailingAddressId,
                      String firstName, String lastName, String phoneNumber,
                      int creditScore, LocalDate birthDate) {
        this.userProfileId = userProfileId;
        this.userId = userId;
        this.mailingAddressId = mailingAddressId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.creditScore = creditScore;
        this.birthDate = birthDate;
    }

    // Getters
    public int getUserProfileId() { return userProfileId; }
    public int getUserId() { return userId; }
    public int getMailingAddressId() { return mailingAddressId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public int getCreditScore() { return creditScore; }
    public LocalDate getBirthDate() { return birthDate; }

    // Setters
    public void setUserProfileId(int userProfileId) { this.userProfileId = userProfileId; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setMailingAddressId(int mailingAddressId) { this.mailingAddressId = mailingAddressId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setCreditScore(int creditScore) { this.creditScore = creditScore; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    @Override
    public String toString() {
        return "UserProfile{" +
                "userProfileId=" + userProfileId +
                ", userId=" + userId +
                ", mailingAddressId=" + mailingAddressId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", creditScore=" + creditScore +
                ", birthDate=" + birthDate +
                '}';
    }
}
