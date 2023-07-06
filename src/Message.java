import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private User sender;
    private User recipientUser;
    private Group recipientGroup;
    private String content;
    private LocalDateTime timestamp;

    // Konstruktor
    public Message(User sender, User recipientUser, String content) {
        this.sender = sender;
        this.recipientUser = recipientUser;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }
    
    // Konstruktor
    public Message(User sender, Group recipientGroup, String content) {
        this.sender = sender;
        this.recipientGroup = recipientGroup;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    // Mendapatkan pengirim
    public User getSender() {
        return sender;
    }

    // Mendapatkan Penerima Chat Private
    public User getRecipientUser() {
        return recipientUser;
    }

    // mendapatkan Penerima Chat Grup
    public Group getRecipientGroup() {
        return recipientGroup;
    }

    // Mendapatkan Isi Chat
    public String getContent() {
        return content;
    }

    // Mendapatkan Timestamp
    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    // Override Kelas Object sehingga menghasilkan data yang dibutuhkan
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedTimestamp = timestamp.format(formatter);

        if (recipientUser != null) {
            return "[" + formattedTimestamp + "] " + sender.getUsername() + ": " + content;
        } else {
            return "[" + formattedTimestamp + "] " + sender.getUsername() + ": " + content;
        }
    }
}

