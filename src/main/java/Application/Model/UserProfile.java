package Application.Model;

public class UserProfile {
    private int userProfileId;
    private int accountId;
    private int mailingAddress;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int creditScore;
    private String birthDate;

    public int getUserProfileId() {        return userProfileId;    }
    public int getAccountId() {        return accountId;    }
    public int getMailingAddress() {
        return mailingAddress;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName(){        return lastName;    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public int getCreditScore() {        return creditScore;    }
    public String getBirthDate() {        return birthDate;    }
    //set

    public void setUserProfileId(int userProfileId) {        this.userProfileId = userProfileId;    }
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    public void setMailingAddress(int mailingAddress) {
        this.mailingAddress = mailingAddress;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setCreditScore(int creditScore) {        this.creditScore = creditScore;    }
    public void setBirthDate(String birthDate) {        this.birthDate = birthDate;    }

    @Override
    public String toString(){
        //pendiente
        return null;
    }
}
