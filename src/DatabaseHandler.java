import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

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
    
    /*
     * Importing user contact for certain User
     */
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
    
    /*
     * save message to database
     * @param recipient = User that receive message
     * @param sender = User that sent message
     */
    public static void createMessage(User recipient, User sender, String content) {
    	
    	List<Chat> listChat = importChat();
    	
    	try {
			
    		String query = "INSERT INTO Message (ChatID, MessageContent,SenderID, RecieverID, MessageDate, MessageTime) VALUES (?, ?, ?, ?, ?, ?)";
    		PreparedStatement st = DB.prepareStatement(query);
    		
    		int pChatID = 0;
    		int recipientID = recipient.getID();
    		int senderID = sender.getID();
    		
    		for (Chat c : listChat) {
    			if (c.getChatParticipant().contains(recipient.getUsername()) && c.getChatParticipant().contains(sender.getUsername())) {
    				pChatID = c.getChatID();
    			} else {
    				createChat(recipient, sender); // create new chat in database
    				createMessage(recipient, sender, content);
    			}
    		}
    		
    		if (pChatID != 0) {
    			st.setInt(1, pChatID);
                st.setString(2, content);
                st.setInt(3, senderID);
                st.setInt(4, recipientID);
                st.setDate(5, Date.valueOf(LocalDate.now()));
                st.setTime(6, Time.valueOf(LocalTime.now()));
                st.execute();
    		} else {
    			throw new SQLException("Failed to sent Message!!");
    		}
    		
    		st.close();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
    	
    }
    
    /*
     * importing chatID
     */
    public static List<Chat> importChat(){
    	List<Chat> listChat = new ArrayList<>();
    	List<User> listUser = importUser();
    	
    	try {
			
    		String query = "Select * from chat";
    		PreparedStatement st = DB.prepareStatement(query);
    		ResultSet rs = st.executeQuery();
    		
    		while (rs.next()) {
    			int ID = rs.getInt("chatID");
    			List<User> participant = new ArrayList<>();
    			
    			for (User u : listUser) {
    				if (rs.getString("P1").equals(u.getUsername()) || rs.getString("P2").equals(u.getUsername())) {
    					participant.add(u);
    				}
    			}
    			
    			Chat chat = new Chat(ID, participant);
    			listChat.add(chat);
    		}
    		
    		st.close();
    		rs.close();
    		
		} catch (SQLException e) {
			e.printStackTrace();		// using printStackTrace because this method are used in development and only behind the screen 
		}
    	
    	return listChat;
    }
    
    public static void createChat(User participant1, User participant2) {
    	
    }

}
