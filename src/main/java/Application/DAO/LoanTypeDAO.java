package Application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Application.Model.LoanType;
import Application.Util.ConnectionUtil;

public class LoanTypeDAO {
    private static final Logger logger = LoggerFactory.getLogger(LoanTypeDAO.class);

    public List<LoanType> getAllLoanTypes() {
        List<LoanType> loanTypes = new ArrayList<>();
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM loans.loan_type";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                loanTypes.add(new LoanType(
                    rs.getInt("loan_type_id"),
                    rs.getString("loan_type")
                ));
            }
        } catch (SQLException e) {
            logger.error("Error retrieving loan types", e);
        }
        return loanTypes;
    }

    public LoanType getLoanTypeById(int id) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM loans.loan_type WHERE loan_type_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new LoanType(
                    rs.getInt("loan_type_id"),
                    rs.getString("loan_type")
                );
            }
        } catch (SQLException e) {
            logger.error("Error retrieving loan type by ID", e);
        }
        return null;
    }

    public LoanType createLoanType(LoanType loanType) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO loans.loan_type (loan_type) VALUES (?) RETURNING loan_type_id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, loanType.getLoanType());
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new LoanType(rs.getInt(1), loanType.getLoanType());
            }
        } catch (SQLException e) {
            logger.error("Error creating loan type", e);
        }
        return null;
    }
}