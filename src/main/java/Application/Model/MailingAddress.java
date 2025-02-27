package Application.Model;

public class MailingAddress {
    private int id;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;

    public MailingAddress(){

    }
    public MailingAddress(String street, String city, String state, String zip, String country){
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
    }
    //gets
    public int getId() {        return id;    }
    public String getCity() {        return city;    }
    public String getCountry() {        return country;    }
    public String getState(){         return state;    }
    public String getStreet() {        return street;    }
    public String getZip() {        return zip;    }
    // sets
    public void setCity(String city) {        this.city = city;    }
    public void setCountry(String country) {        this.country = country;    }
    public void setId(int id) {        this.id = id;    }
    public void setState(String state) {        this.state = state;    }
    public void setStreet(String street) {        this.street = street;    }
    public void setZip(String zip) {        this.zip = zip;    }
    @Override
    public String toString(){
        return "id = "+id +
                " street : "+street+
                " city : "+city+
                " state : "+state+
                " country : "+country+
                " zip : "+zip;
    }
}
