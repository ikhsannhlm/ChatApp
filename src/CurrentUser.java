import java.util.ArrayList;
import java.util.List;

public class CurrentUser extends User {
    private List<User> contacts;

    public CurrentUser(String username, String number) {
        super(username, number);
        contacts = new ArrayList<>();
    }

    public void addContact(User user) {
        contacts.add(user);
    }

    public List<User> getContacts() {
        return contacts;
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
