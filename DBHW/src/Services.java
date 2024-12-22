import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Services {

    public static void ShowCurrentService() throws Exception{
        System.out.println("Current Service");

        Connection con = DBConnecter.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from service where service_end = false order by room_id");
        while(rs.next()){
            System.out.println(rs.getRow());
        }
    }
}
