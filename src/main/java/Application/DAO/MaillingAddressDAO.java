package Application.DAO;

import Application.Model.Account;
import Application.Model.MailingAddress;

import java.util.List;

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
