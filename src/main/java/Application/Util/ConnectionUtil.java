package Application.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    //url will represent our connection string. Since this is an in-memory db, we will represent a file location to store the data
    private static String url = "jdbc:postgresql://localhost:5432/loans";
    private static String username = "postgres";
    private static String password = "admin";

    private static Connection connection = null;

    /**
     * @return active connection to the database
     */
    public static Connection getConnection(){
        if(connection == null){
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
