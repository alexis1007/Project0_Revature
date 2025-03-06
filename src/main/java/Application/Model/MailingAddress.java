package Application.Model;

public class MailingAddress {
    private int mailingAddressId; 
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;

    // Default constructor
    public MailingAddress() {}

    // Parameterized constructor
    public MailingAddress(int mailingAddressId, String street, String city, 
                         String state, String zip, String country) {
        this.mailingAddressId = mailingAddressId;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    // Constructor without ID for creation
    public MailingAddress(String street, String city, String state, 
                         String zip, String country) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }

    // Getters
    public int getMailingAddressId() { return mailingAddressId; }
    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getZip() { return zip; }
    public String getCountry() { return country; }

    // Setters
    public void setMailingAddressId(int mailingAddressId) { 
        this.mailingAddressId = mailingAddressId; 
    }
    public void setStreet(String street) { this.street = street; }
    public void setCity(String city) { this.city = city; }
    public void setState(String state) { this.state = state; }
    public void setZip(String zip) { this.zip = zip; }
    public void setCountry(String country) { this.country = country; }

    @Override
    public String toString() {
        return "MailingAddress{" +
                "mailingAddressId=" + mailingAddressId +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
