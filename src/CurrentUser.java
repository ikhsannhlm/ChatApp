import java.util.List;
import java.util.Map;

public class CurrentUser extends User {
	/*
	 * CurrentUser
	 * A class that handle a user that currently active. 
	 * Made User can sent Message to other User that in vUserContactList and
	 * Also provide them with Chat History (vUserChatList)
	 */
    protected List<User> vUserContactList;
    protected Map<Integer, List<Message>> vUserChatList;
    

    /*
     * Constructor of CurrentUser, Copy existing User from SuperClass and then add its ContactList
     * (Future update might have this class hold GroupList too)
     */
    public CurrentUser(User pUser) {
        super(pUser);
        vUserContactList = DatabaseHandler.importContact(pUser.getUsername());
        vUserChatList = DatabaseHandler.importMessage(pUser.getUsername());
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
    	/*
    	List<Chat> hChatList = DatabaseHandler.importChat();
    	for (Chat c : hChatList) {
    		if (c.getChatParticipantUsername().contains(pRecipient.getUsername()) && c.getChatParticipantUsername().contains(this.getUsername())) {
    			Message m = new Message(c.getChatID(), this, pRecipient, pMessageContent);
    			pRecipient.receiveMessage(c.getChatID(), m);
    		}
    	}
    	*/
    	// save to Database -> invoke createMessage method to save Message to database for Message history after user turn off the program
    	DatabaseHandler.createMessage(pRecipient, this, pMessageContent);
    	updateChatList();
    }

    public void updateChatList() {
    	vUserChatList = DatabaseHandler.importMessage(this.getUsername());
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
