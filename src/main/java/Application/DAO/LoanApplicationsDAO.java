package Application.DAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Application.Model.LoanApplication;
import Application.Util.ConnectionUtil;

public class LoanApplicationsDAO {
    private final String url;
    private final String dbUser;
    private final String dbPassword;

    public LoanApplicationsDAO(String url, String dbUser, String dbPassword) {
        this.url = url;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }
    public List<LoanApplication> getAllLoans(){
        Connection connection = ConnectionUtil.getConnection();
        List<LoanApplication> loanApplications = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM loan_applications";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){

                // pendiente

                //LoanApplication loanApplication = new LoanApplication(rs.getInt("loand_aplication_id"));
                //loanApplications.add(loanApplication);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return loanApplications;

    }
    public LoanApplication getLoanById(){
        return null;
    }
    public void updateLoan(){

    }
    public LoanApplication addLoan(){
        return null;
    }
    

    public void  deleteLoanApplication(){
        String sql = "DELETE FROM loan_applications WHERE id = ?;";
        try (Connection conn = DriverManager.getConnection(url,dbUser,dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)){
            //stmt.setInt();
            //stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
