import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
	private static Connection DB = DBConnection.getDB();
	
	public static boolean createUser(String username, String number){
        List<User> userList= importUser();
        boolean result = false;
        try {
            
            for (User user : userList) {
                if (user.getUsername().equals(username) && user.getNumber().equals(number)) {
                    throw new SQLException("User with the same username and number already exists. Please try again.\n");
                }
            }
            
            if (username.isEmpty()) {
            	throw new SQLException("Username cant be empty");
            }
            if (number.isEmpty()) {
            	throw new SQLException("Number cant be empty");
            }

            String insert = "INSERT INTO Account (AccountName, AccountNumber) VALUES (?,?)";
            PreparedStatement st = DB.prepareStatement(insert);
            st.setString(1,username);
            st.setString(2,number);
            st.executeUpdate();
            
            st.close();
            
            result = true;
            
        }
        catch(SQLException E){
            E.printStackTrace();
        }
        
        return result;
    }

    public static List<User> importUser(){
        List<User> tUserList = new ArrayList<>();
        try {
            String query = "SELECT * FROM Account";
            PreparedStatement st = DB.prepareStatement(query);
            ResultSet rs = st.executeQuery();

            while (rs.next()){
                String tAccountName = rs.getString(2);
                String tAccountNumber = rs.getString(3);

                User tUser = new User(tAccountName, tAccountNumber);
                tUserList.add(tUser);
            }
            st.close();
            rs.close();
        }
        catch(SQLException E){
            E.printStackTrace();
        }
        return tUserList;
    }
    
    public static List<User> importContact(String pUsername){
    	List<User> contactList = new ArrayList<>();
    	List<User> userList = importUser();
    	
    	try {
			
    		String query = "select group_concat(distinct a2.AccountName) as ContactOf, a.AccountName as UserContact from contact c join account a on a.AccountID = c.ContactAccount join account a2 on a2.AccountID = c.ContactOf where a.AccountName = \'" + pUsername + "\';";
    		PreparedStatement st = DB.prepareStatement(query);
    		ResultSet rs = st.executeQuery();
    		
    		while (rs.next()) {
    			User tUser = null;
    			
    			String[] tUserContact = rs.getString(1).split(","); 
    			
    			for (User u : userList) {
    				if (rs.getString(2).equals(u.getUsername())) {
    					tUser = u;
    					break;
    				}
    			}
    			for (User u : userList) {
    				for (String c : tUserContact) {
    					if (c.equals(u.getUsername())) {
    						contactList.add(u);
    					}
    				}
    			}
    		}
    		
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    	return contactList;
    }
}
