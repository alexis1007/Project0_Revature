package Application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Application.Model.LoanApplication;
import Application.Util.ConnectionUtil;

public class LoanApplicationsDAO {
    private static final Logger logger = LoggerFactory.getLogger(LoanApplicationsDAO.class);

    public LoanApplicationsDAO() {
    }

    public List<LoanApplication> getAllLoans() {
        List<LoanApplication> loans = new ArrayList<>();
        Connection conn = ConnectionUtil.getConnection();
        //System.out.println(" x");
        try {
            String sql = "SELECT * FROM loans.loan_applications";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                loans.add(mapResultSetToLoanApplication(rs));
            }
        } catch(SQLException e) {
            logger.error("Error getting all loans", e);
        }
        return loans;
    }

    public List<LoanApplication> getLoansByUserId(int userId) {
        List<LoanApplication> loans = new ArrayList<>();
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM loans.loan_applications WHERE created_by = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                loans.add(mapResultSetToLoanApplication(rs));
            }
        } catch(SQLException e) {
            logger.error("Error getting loans by user ID", e);
        }
        return loans;
    }

    public LoanApplication getLoanById(int loanId) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM loans.loan_applications WHERE loan_applications_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, loanId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return mapResultSetToLoanApplication(rs);
            }
        } catch(SQLException e) {
            logger.error("Error getting loan by ID", e);
        }
        return null;
    }

    public LoanApplication createLoan(LoanApplication loan) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO loans.loan_applications " +
                        "(loan_type_id, application_statuses_id, user_profiles_id, " +
                        "principal_balance, interest, term_length, total_balance, " +
                        "borrower, application_date, created_by, created_at) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP) " +
                        "RETURNING *";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, loan.getLoanTypeId());
            ps.setInt(2, loan.getApplicationStatusId());
            ps.setInt(3, loan.getUserProfileId());
            ps.setBigDecimal(4, loan.getPrincipalBalance());
            ps.setBigDecimal(5, loan.getInterest());
            ps.setInt(6, loan.getTermLength());
            ps.setBigDecimal(7, loan.getTotalBalance());
            ps.setString(8, loan.getBorrower());
            ps.setInt(9, loan.getCreatedBy());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToLoanApplication(rs);
            } else {
                logger.error("No rows returned after insert");
                throw new RuntimeException("Failed to create loan application");
            }
        } catch (SQLException e) {
            logger.error("Error creating loan: {}", e.getMessage());
            throw new RuntimeException("Error creating loan", e);
        }
    }

    public void updateLoanStatus(int loanId, int statusId, int userId) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE loans.loan_applications SET application_statuses_id = ?, " +
                        "status_changed_by = ?, status_changed_at = CURRENT_TIMESTAMP " +
                        "WHERE loan_applications_id = ?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, statusId);
            ps.setInt(2, userId);
            ps.setInt(3, loanId);
            ps.executeUpdate();
        } catch(SQLException e) {
            logger.error("Error updating loan status", e);
        }
    }

    public LoanApplication updateLoan(LoanApplication loan) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE loans.loan_applications SET " +
                "loan_type_id = ?, " +
                "principal_balance = ?, " +
                "interest = ?, " +
                "term_length = ?, " +
                "total_balance = ?, " +
                "borrower = ?, " +
                "updated_at = CURRENT_TIMESTAMP, " +
                "updated_by = ? " +
                "WHERE loan_applications_id = ? " +
                "RETURNING *";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, loan.getLoanTypeId());
            ps.setBigDecimal(2, loan.getPrincipalBalance());
            ps.setBigDecimal(3, loan.getInterest());
            ps.setInt(4, loan.getTermLength());
            ps.setBigDecimal(5, loan.getTotalBalance());
            ps.setString(6, loan.getBorrower());
            ps.setInt(7, loan.getCreatedBy());
            ps.setInt(8, loan.getLoanApplicationId());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToLoanApplication(rs);
            }
            
            logger.info("Loan updated successfully: {}", loan.getLoanApplicationId());
            return loan;
        } catch (SQLException e) {
            logger.error("Error updating loan: {}", e.getMessage());
            throw new RuntimeException("Error updating loan", e);
        }
    }

    private LoanApplication mapResultSetToLoanApplication(ResultSet rs) throws SQLException {
        return new LoanApplication(
            rs.getInt("loan_applications_id"),
            rs.getInt("loan_type_id"),
            rs.getInt("application_statuses_id"),
            rs.getInt("user_profiles_id"),
            rs.getBigDecimal("principal_balance"),
            rs.getBigDecimal("interest"),
            rs.getInt("term_length"),
            rs.getBigDecimal("total_balance"),
            rs.getString("borrower"),
            rs.getTimestamp("application_date").toLocalDateTime(),
            rs.getInt("created_by"),
            rs.getTimestamp("created_at").toLocalDateTime(),
            rs.getObject("updated_by", Integer.class),
            rs.getObject("updated_at", Timestamp.class) != null ? 
                rs.getTimestamp("updated_at").toLocalDateTime() : null,
            rs.getObject("status_changed_by", Integer.class),
            rs.getObject("status_changed_at", Timestamp.class) != null ? 
                rs.getTimestamp("status_changed_at").toLocalDateTime() : null
        );
    }

    public void deleteLoanApplication(int loanId) {
        Connection conn = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM loans.loan_applications WHERE loan_applications_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, loanId);
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected == 0) {
                logger.warn("No loan found to delete with ID: {}", loanId);
                throw new RuntimeException("Loan not found");
            }
            
            logger.info("Loan application deleted successfully: {}", loanId);
        } catch (SQLException e) {
            logger.error("Error deleting loan application: {}", e.getMessage());
            throw new RuntimeException("Error deleting loan application", e);
        }
    }
}
