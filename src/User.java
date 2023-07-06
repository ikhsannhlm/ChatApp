import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String username;
    private String number;
    private List<User> contactList;
    private List<Message> chatList;

    public User(String username, String number) {
        this.username = username;
        this.number = number;
        this.contactList = new ArrayList<>();
        this.chatList = new ArrayList<>();
    }

    // Mendapatkan Username User
    public String getUsername() {
        return username;
    }

    // Mendapatkan Nomor User
    public String getNumber() {
        return number;
    }

    // Menambahkan Kontak kedalam List Kontak User
    public void addContact(User user) {
        contactList.add(user);
    }

    // Menampilkan Daftar list Kontak
    public void displayContactList() {
        System.out.println("Contact " + getUsername() + ": ");
        if (contactList.isEmpty()) {
            System.out.println("There's no contacts");
        } else {
            for (User contact : contactList) {
                System.out.println(contact.getUsername() + " (" + contact.getNumber() + ")");
            }
        }
        System.out.println();
    }
    
    // Mengirimkan Chat
    public void sendMessage(User recipient, String content) {
        Message message = new Message(this, recipient, content);
        recipient.receiveMessage(message);
        chatList.add(message);
    }

    // Menerima Chat dan menyimpannya ke dalam chatList 
    public void receiveMessage(Message message) {
        chatList.add(message);
    }

    // Menampilkan List Chat Private User
    public void displayChatList(User user) {
        System.out.println("Chat List for User: " + username + " with "+ user);
        boolean found = false;
        for (Message message : chatList) {
            if ((message.getSender().equals(this) && message.getRecipientUser().equals(user))
                    || (message.getSender().equals(user) && message.getRecipientUser().equals(this))) {
                System.out.println(message);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No messages found");
        }
        System.out.println();
    }

    // Menampilkan List Chat User dengan User lain
    public void displayReceivedChat() {    
        Map<User, Message> lastMessages = new HashMap<>();
        for (Message message : chatList) {
            if (message.getRecipientUser() != null && message.getRecipientUser().equals(this)) {
                User sender = message.getSender();
                lastMessages.put(sender, message);
            }
        }
    
        if (lastMessages.isEmpty()) {
            System.out.println("No received messages found");
        } else {
            for (Message message : lastMessages.values()) {
                String sender = message.getSender().getUsername();
                String content = message.getContent();
                System.out.println(sender + ": " + content);
            }
        }
        System.out.println();
    }
    
    // Mendapatkan objek User yang sesuai dengan nama penerima
    public User getContactByUsername(String username) {
        for (User contact : contactList) {
            if (contact.getUsername().equals(username)) {
                return contact;
            }
        }
        return null;
    }

    public static boolean createUser(String username, String number){
        Connection DB = DBConnection.getDB();
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
        Connection DB = DBConnection.getDB();
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
}