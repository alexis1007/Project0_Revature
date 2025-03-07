package Application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Application.Model.MailingAddress;
import Application.Util.ConnectionUtil;

public class MailingAddressDAO {
    private static final Logger logger = LoggerFactory.getLogger(MailingAddressDAO.class);

    public List<MailingAddress> getAllMailingAddresses() {
        List<MailingAddress> addresses = new ArrayList<>();
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM loans.mailing_addresses";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                addresses.add(new MailingAddress(
                    rs.getInt("mailing_addresses_id"),
                    rs.getString("street"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getString("zip"),
                    rs.getString("country")
                ));
            }
        } catch (SQLException e) {
            logger.error("Error getting all mailing addresses", e);
        }
        return addresses;
    }

    public MailingAddress getMailingAddressById(int id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM loans.mailing_addresses WHERE mailing_addresses_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new MailingAddress(
                    rs.getInt("mailing_addresses_id"),
                    rs.getString("street"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getString("zip"),
                    rs.getString("country")
                );
            }
        } catch (SQLException e) {
            logger.error("Error getting mailing address by ID", e);
        }
        return null;
    }

    public MailingAddress createMailingAddress(MailingAddress address) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            // Modificar la consulta para usar SERIAL y dejar que la base de datos asigne el ID
            String sql = "INSERT INTO loans.mailing_addresses (street, city, state, zip, country) " +
                        "VALUES (?, ?, ?, ?, ?) " +
                        "RETURNING mailing_addresses_id, street, city, state, zip, country";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, address.getStreet());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getState());
            ps.setString(4, address.getZip());
            ps.setString(5, address.getCountry());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                MailingAddress created = new MailingAddress(
                    rs.getInt("mailing_addresses_id"),
                    rs.getString("street"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getString("zip"),
                    rs.getString("country")
                );
                logger.info("Created mailing address with ID: {}", created.getMailingAddressId());
                return created;
            }
        } catch (SQLException e) {
            logger.error("Error creating mailing address: {}", e.getMessage());
            throw new RuntimeException("Error creating mailing address", e);
        }
        return null;
    }

    public void updateMailingAddress(int id, MailingAddress address) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE loans.mailing_addresses SET street=?, city=?, state=?, zip=?, country=? " +
                        "WHERE mailing_addresses_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, address.getStreet());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getState());
            ps.setString(4, address.getZip());
            ps.setString(5, address.getCountry());
            ps.setInt(6, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error updating mailing address", e);
        }
    }

    public void deleteMailingAddress(int id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM loans.mailing_addresses WHERE mailing_addresses_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error deleting mailing address", e);
        }
    }
}
