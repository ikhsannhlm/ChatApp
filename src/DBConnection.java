import java.sql.*;

public class DBConnection {
    private static Connection vMysql;
    private final static String url= "jdbc:mysql://192.168.56.1:3306/Chat";
    private final static String username= "Dan";
    private final static String password= "1217050070";
    
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