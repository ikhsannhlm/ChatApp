import java.sql.*;

public class DBConnection {
    private static Connection vMysql;
    private final static String url= "jdbc:mysql://localhost:3306/Chat";
    private final static String username= "root";
    private final static String password= "root";
    
    public static Connection getDB(){
        if (vMysql==null){
            try {
                vMysql=DriverManager.getConnection(url, username, password);
            }
            catch(SQLException E ){
                E.printStackTrace();
            }
        }
        return vMysql;
    }
}