import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

			String hRecipientName = recipient.getUsername();
			String hSenderName = sender.getUsername();
    		for (Chat c : listChat) {
    			
    			if (c.getChatParticipantUsername().contains(hRecipientName) && c.getChatParticipantUsername().contains(hSenderName)) {
    				pChatID = c.getChatID();
    			}
    			
    		}
    		
    		if (pChatID == 0){
				pChatID = createChat(recipient, sender); // create new chat in database then ivoke itself to get new ChatID
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
     * import Map of Message for certain User
     * @param pUsername = Username of User that the Message will be imported, which is CurrentUser
     */
    public static Map<Integer, List<Message>> importMessage(String pUsername){
    	Map<Integer, List<Message>> userMessageList = new HashMap<>();
    	List<Chat> chatList = importChat();
    	
    	try {
			
    		String query = "SELECT m.ChatID, a.AccountName AS Sender, a2.AccountName AS Receiver, m.MessageContent, m.MessageDate, m.MessageTime "
                    + "FROM message m JOIN account a ON a.AccountID = m.SenderID "
                    + "JOIN account a2 ON a2.AccountID = m.RecieverID WHERE a.AccountName = ? OR a2.AccountName = ? ORDER BY m.ChatID, m.MessageDate, m.MessageTime";
            PreparedStatement st = DB.prepareStatement(query);
            st.setString(1, pUsername);
            st.setString(2, pUsername);
            ResultSet rs = st.executeQuery();
            
            // Retrieve all Message of a User
            List<Message> messageList = new ArrayList<>();
            while (rs.next()) {
            	int tChatID = rs.getInt("ChatID");
            	String tParticipant1 = rs.getString("Sender");
            	String tParticipant2 = rs.getString("Receiver");
            	User tSender = null;
            	User tReceiver = null;
            	String tMessageContent = rs.getString("MessageContent");
            	LocalDate tMessageDate = rs.getDate("MessageDate").toLocalDate();
            	LocalTime tMessageTime = rs.getTime("MessageTime").toLocalTime();
            	
            	for (Chat c : chatList) {
            		if (c.getChatID() == tChatID) {
            			for (User u : c.getChatParticipant()) {
            				if (u.getUsername().equals(tParticipant1)) {
            					tSender = u;
            				}
            				if (u.getUsername().equals(tParticipant2)) {
            					tReceiver = u;
            				}
            			}
            		}
            	}
            	
            	Message message = new Message(tChatID, tSender, tReceiver, tMessageContent);
            	message.setMessageDateTime(tMessageDate, tMessageTime);
            	
            	messageList.add(message);
            }
    		
            // Divided messageList to their Corresponding ChatID then put it to Map
            for (Message message : messageList) {
                int chatID = message.getChatID();
                List<Message> chatMessageList = userMessageList.getOrDefault(chatID, new ArrayList<>());
                chatMessageList.add(message);
                userMessageList.put(chatID, chatMessageList);
            }
    		
    		st.close();
    		rs.close();
    		
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return userMessageList;
    }
    
    /*
     * importing chatID
     */
    public static List<Chat> importChat(){
    	List<Chat> listChat = new ArrayList<>();
    	List<User> listUser = importUser();
    	
    	try {
			
    		String query = "select c.ChatID, a.AccountName as P1, a2.AccountName as P2 from chat c join account a on c.P1 = a.AccountID \r\n"
    				+ "join account a2 on a2.AccountID = c.P2";
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
    
    /*
     * @return hChatID = retrieve new generated ChatID if chat doesnt exist (in createMessage)
     */
    public static int createChat(User participant1, User participant2) {
    	int hChatID = 0;
    	try {
    		
			String query = "Insert into chat (P1, P2) values (?, ?)";
			PreparedStatement st = DB.prepareStatement(query);
			st.setInt(1, participant1.getID());
			st.setInt(2, participant2.getID());
			
			st.executeUpdate();
			
			ResultSet generatedKeys = st.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            hChatID = generatedKeys.getInt(1);
	        } else {
	            throw new SQLException("Failed to retrieve the generated Chat ID.");
	        }
			
			st.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return hChatID;
    }

}
