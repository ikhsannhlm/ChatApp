import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurrentUser extends User {
	/*
	 * CurrentUser
	 * A class that handle a user that currently active. 
	 * Made User can sent Message to other User that in vUserContactList and
	 * Also provide them with Chat History (vUserChatList)
	 */
    private List<User> vUserContactList;
    private Map<Integer, List<Message>> vUserChatList;

    /*
     * Constructor of CurrentUser, Copy existing User from SuperClass and then add its ContactList
     * (Future update might have this class hold GroupList too)
     */
    public CurrentUser(User pUser) {
        super(pUser);
        vUserContactList = DatabaseHandler.importContact(pUser.getUsername());
    }
    
    /*
     * Displaying Current User Contact List
     */
    public void displayContactList() {
        System.out.println("Contact " + getUsername() + ": ");
        if (vUserContactList.isEmpty()) {
            System.out.println("There's no contacts");
        } else {
            for (User contact : vUserContactList) {
                System.out.println(contact.getUsername() + " (" + contact.getNumber() + ")");
            }
        }
        System.out.println();
    }
    
    /*
     * Allow Current User to choose a User from their Contact List to Sent them a Message
     */
    public void sentPersonalChat() {
    	if (vUserContactList.isEmpty()) {
            System.out.println("There's no contacts");
        } else {
        	System.out.println("Pilih contact untuk memulai Chat: ");
            for (User contact : vUserContactList) {
                System.out.println(contact.getUsername() + " (" + contact.getNumber() + ")");
            }
            
            String username = GlobalScanner.getScanner().nextLine();
            
            for (User contact : vUserContactList) {
            	if (contact.getUsername().equalsIgnoreCase(username)) {
            		System.out.print("Message Content: ");
            		String content = GlobalScanner.getScanner().nextLine();
            		sentMessage(contact, content);
            	}
            }
        }
    }
    
    /*
     * Method to sent a Message to other User
     * @param pRecipient = User that receive Message
     * @param pMessageContent = Content of Message
     */
    public void sentMessage(User pRecipient, String pMessageContent) {
    	// sent message -> invoke recieveMessage method of recipient, allowing recipient to add the Message to their chatList 
    	List<Chat> hChatList = DatabaseHandler.importChat();
    	Message m = new Message(this, pRecipient, pMessageContent);
    	for (Chat c : hChatList) {
    		if (c.getChatParticipant().contains(pRecipient.getUsername()) && c.getChatParticipant().contains(this.getUsername())) {
    			receiveMessage(c.getChatID(), m);
    		}
    	}
    	// save to Database -> invoke createMessage method to save Message to database for Message history after user turn off the program
    	DatabaseHandler.createMessage(pRecipient, this, pMessageContent);
    }
    
    /*
     * Method to receive a Message from other User, Updating vUserChatList for certain ID
     * @param pChatID = ID that assigned between two User
     * @param pMessage = Message that will be saved to messageList
     */
    public void receiveMessage(int pChatID, Message pMessage) {
    	// Update List<Message> by its ChatID (key of Map)
    	List<Message> messageList = vUserChatList.get(pChatID);
        if (messageList == null) {
            messageList = new ArrayList<>();
            vUserChatList.put(pChatID, messageList);
        }
        messageList.add(pMessage);
        vUserChatList.put(pChatID, messageList);
    }

    /*
     * 
     *
    public void addContact(User user) {
        vUserContactList.add(user);
        
    }

    public List<User> getContacts() {
        return vUserContactList;
    }
    */
    
    public Message getLastMessageWithUser(User user) {
        // Implement logic to retrieve the last message between the current user and the specified user
        // ...
        return null; // Replace null with the actual last message
    }

    //Menampilkan Info User
        public void displayUserInfo() {
        System.out.println("Username: " + getUsername());
        System.out.println("Number: " + getNumber());
    }
}
