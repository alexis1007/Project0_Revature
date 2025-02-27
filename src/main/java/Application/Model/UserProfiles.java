package Application.Model;

public class UserProfiles {
    int accountId;
    String firstName;
    String lastName;
    String phoneNumber;
    String mailingAddress;

    public int getAccountId() {
        return accountId;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getMailingAddress() {
        return mailingAddress;
    }
    //set
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
