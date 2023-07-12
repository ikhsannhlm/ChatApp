import java.time.LocalDate;
import java.time.LocalTime;

public class Message {
	private int vChatID;
    private User sender;
    private User recipientUser;
    private String content;
    private LocalDate vMessageDate;
    private LocalTime vMessageTime;
    //private Group recipientGroup;

    /*
     * Constructor of Message
     * @param pChatID = unique key that identify where this Message belongs to
     * @param sender = User that send this Message
     * @param recipientUser = User that receive the Message
     * @param content = content of Message (max around 5000 char including white space)
     */
    public Message(int pChatID, User sender, User recipientUser, String content) {
    	this.vChatID = pChatID;
        this.sender = sender;
        this.recipientUser = recipientUser;
        this.content = content;
    }
    
    /*
     * Set Date and Time of a Message
     * @param pDate = Date of Message
     * @param pTime = Time of Message
     */
    public void setMessageDateTime(LocalDate pDate, LocalTime pTime) {
    	if (pDate == null && pTime == null) {
    		this.vMessageDate = LocalDate.now();
            this.vMessageTime = LocalTime.now();
    	} else {
    		this.vMessageDate = pDate;
    		this.vMessageTime = pTime;
    	}
    }
    
    /*
     * @return unique key of the Message
     */
    public int getChatID() {
    	return vChatID;
    }
    
    /*
     * @return Date of Message
     */
    public LocalDate getChatDate() {
    	return vMessageDate;
    }
    
    /*
     * @return String of detailed Message
     */
    public String toString() {
    	String usernameAndNumber = "\u001B[92m" + sender.getUsername() + " (" + sender.getNumber() + ")" + "\u001B[0m";
    	
    	String formattedString = String.format("%-30s %-10s%n%s", usernameAndNumber, vMessageTime, content);
    	return formattedString;
    }
    
/*
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
*/
}

