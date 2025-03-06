package Application.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Application.DAO.MailingAddressDAO;
import Application.Model.MailingAddress;

public class MailingAddressService {
    private static final Logger logger = LoggerFactory.getLogger(MailingAddressService.class);
    private final MailingAddressDAO mailingAddressDAO;

    public MailingAddressService() {
        this.mailingAddressDAO = new MailingAddressDAO();
    }

    public MailingAddressService(MailingAddressDAO mailingAddressDAO) {
        this.mailingAddressDAO = mailingAddressDAO;
    }

    public List<MailingAddress> getAllMailingAddresses() {
        return mailingAddressDAO.getAllMailingAddresses();
    }

    public MailingAddress getMailingAddressById(int id) {
        return mailingAddressDAO.getMailingAddressById(id);
    }

    public MailingAddress createMailingAddress(MailingAddress address) {
        if (isValidAddress(address)) {
            return mailingAddressDAO.createMailingAddress(address);
        }
        return null;
    }

    public void updateMailingAddress(int id, MailingAddress address) {
        if (isValidAddress(address)) {
            mailingAddressDAO.updateMailingAddress(id, address);
        }
    }

    public void deleteMailingAddress(int id) {
        mailingAddressDAO.deleteMailingAddress(id);
    }

    private boolean isValidAddress(MailingAddress address) {
        return address != null &&
               address.getStreet() != null && !address.getStreet().trim().isEmpty() &&
               address.getCity() != null && !address.getCity().trim().isEmpty() &&
               address.getState() != null && !address.getState().trim().isEmpty() &&
               address.getZip() != null && !address.getZip().trim().isEmpty() &&
               address.getCountry() != null && !address.getCountry().trim().isEmpty();
    }
}
