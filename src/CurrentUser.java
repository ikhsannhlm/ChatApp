import java.util.ArrayList;
import java.util.List;

public class CurrentUser extends User {
    private List<User> contactList;
    private List<Message> chatList;

    public CurrentUser(User pUser) {
        super(pUser);
        contactList = DatabaseHandler.importContact(pUser.getUsername());
    }

    public void addContact(User user) {
        contactList.add(user);
    }

    public List<User> getContacts() {
        return contactList;
    }
    
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
    
    public void showPersonalChatMenu() {
    	if (contactList.isEmpty()) {
            System.out.println("There's no contacts");
        } else {
        	System.out.println("Pilih contact untuk memulai Chat: ");
            for (User contact : contactList) {
                System.out.println(contact.getUsername() + " (" + contact.getNumber() + ")");
            }
            
            String username = GlobalScanner.getScanner().nextLine();
            
            for (User contact : contactList) {
            	if (contact.getUsername().equals(username)) {
            		System.out.print("Message Content: ");
            		String content = GlobalScanner.getScanner().nextLine();
            		sentMessage(contact, content);
            	}
            }
        }
    }
    
    public void sentMessage(User recipient, String content) {
    	// sent message
    	// save to Database
    }

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
