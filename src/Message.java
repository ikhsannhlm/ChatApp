import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Message {
	private int vChatID;
    private User sender;
    private User recipientUser;
    private String content;
    private LocalDate vMessageDate;
    private LocalTime vMessageTime;
    private Group recipientGroup;

    // Konstruktor
    public Message(int pChatID, User sender, User recipientUser, String content) {
    	this.vChatID = pChatID;
        this.sender = sender;
        this.recipientUser = recipientUser;
        this.content = content;
    }
    
    public void setMessageDateTime(LocalDate pDate, LocalTime pTime) {
    	if (pDate == null && pTime == null) {
    		this.vMessageDate = LocalDate.now();
            this.vMessageTime = LocalTime.now();
    	} else {
    		this.vMessageDate = pDate;
    		this.vMessageTime = pTime;
    	}
    }
    
    public int getChatID() {
    	return vChatID;
    }
    
    // Konstruktor
    public Message(User sender, Group recipientGroup, String content) {
        this.sender = sender;
        this.recipientGroup = recipientGroup;
        this.content = content;
        this.vMessageDate = LocalDate.now();
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
/*    public LocalDateTime getTimestamp() {
        return vMessageDate;
    }*/


    // Override Kelas Object sehingga menghasilkan data yang dibutuhkan
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedTimestamp = vMessageDate.format(formatter);

        if (recipientUser != null) {
            return "[" + formattedTimestamp + "] " + sender.getUsername() + ": " + content;
        } else {
            return "[" + formattedTimestamp + "] " + sender.getUsername() + ": " + content;
        }
    }
}

