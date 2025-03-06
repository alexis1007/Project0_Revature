package Application.DAO;


import java.util.List;

import Application.Model.MailingAddress;

public class MaillingAddressDAO {
    private final String url;
    private final String dbUser;
    private final String dbPassword;

    public MaillingAddressDAO(String url, String dbUser, String dbPassword) {
        this.url = url;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }
    public MailingAddress createMailingAddress(MailingAddress mailingAddress){
        return null;
    }
    public List<MailingAddress> getAllMailingAddress(){
        return null;
    }
    public void updateMailingAddress(MailingAddress mailingAddress){

    }
    public void deleteMailingAddress(MailingAddress mailingAddress){

    }
}
