import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
	/*
	 * Implement DAO Pattern from Structural Design Pattern. This pattern encapsulate Data Access logic, keeping other code to keep clean.
	 * Letting other class to interact with Database indirectly.
	 */
	private static Connection DB = DBConnection.getDB();
	
	/*
	 * createUser
	 * This method used to create and save new user account into database. Taking username and number as parameter.
	 */
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

	/*
	 * importUser
	 * This method import user account from database.
	 */
    public static List<User> importUser(){
        List<User> tUserList = new ArrayList<>();
        try {
            String query = "SELECT * FROM Account";
            PreparedStatement st = DB.prepareStatement(query);
            ResultSet rs = st.executeQuery();

            while (rs.next()){
            	int tAccountID = rs.getInt(1);
                String tAccountName = rs.getString(2);
                String tAccountNumber = rs.getString(3);

                User tUser = new User(tAccountID, tAccountName, tAccountNumber);
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
    		
    		 if (rs.next()) {
    			 String contactString = rs.getString(1);
    	         if (contactString != null && !contactString.isEmpty()) {
    	        	 String[] tUserContact = contactString.split(",");

    	             for (User u : userList) {
    	            	 for (String c : tUserContact) {
    	            		 if (c.equals(u.getUsername())) {
    	            			 contactList.add(u);
    	            		 }
    	            	 }
    	             }
    	             
    	         }
    	         
    		 }
    		
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return contactList;
    }
    
}
