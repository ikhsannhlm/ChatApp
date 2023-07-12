import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
	private int ID;
    private String username;
    private String number;

    public User(int ID, String username, String number) {
    	this.ID = ID;
        this.username = username;
        this.number = number;
    }
    
    public User(User pUser) {
    	this.ID = pUser.ID;
    	this.username = pUser.username;
    	this.number = pUser.number;
    }
	
    // Mendapatkan Username User
    public String getUsername() {
        return username;
    }

    // Mendapatkan Nomor User
    public String getNumber() {
        return number;
    }
    
    // get ID, simply used to interact with Database
    public int getID() {
    	return ID;
    }
    
/*    
    *
     * Method to receive a Message from other User, Updating vUserChatList for certain ID
     * @param pChatID = ID that assigned between two User
     * @param pMessage = Message that will be saved to messageList
     *
    public void receiveMessage(int pChatID, Message pMessage) {
    	// Update List<Message> by its ChatID (key of Map)
    	List<Message> messageList = vUserChatList.getOrDefault(pChatID, new ArrayList<>());
        if (messageList == null) {
            messageList = new ArrayList<>();
            vUserChatList.put(pChatID, messageList); // i think this one are unnecessary
        }
        messageList.add(pMessage);
        vUserChatList.put(pChatID, messageList);
    }
    
	
 * Code in this section might be move to CurrentUser
    // Menambahkan Kontak kedalam List Kontak User
    public void addContact(User user) {
        contactList.add(user);
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
*/
    
}