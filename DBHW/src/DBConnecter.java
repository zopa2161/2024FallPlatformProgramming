
import java.sql.*;


public class DBConnecter {
    public final static String DB_DRIVER_CLASS = "org.postgresql.Driver";
    public final static String DB_URL = "jdbc:postgresql://localhost:5432/hoteldata";
    public final static String DB_USERNAME = "postgres";
    public final static String DB_PASSWORD = "1738";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        Connection con = null;

        // load the Driver Class
        Class.forName(DB_DRIVER_CLASS);

        // create the connection now
        con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        //System.out.println("DB Connection created successfully");
        return con;
    }


}
