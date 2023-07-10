import java.util.ArrayList;
import java.util.List;

public class Chat {
	private int chatID;
	private List<User> participant;
	
	public Chat (int chatID, List<User> participant) {
		this.chatID = chatID;
		this.participant = participant;
	}
	
	public int getID() {
		return chatID;
	}
	
	public List<String> getParticipant(){
		List<String> participantUsername = new ArrayList<>();
		
		for (User u : participant) {
			participantUsername.add(u.getUsername());
		}
		
		return participantUsername;
	}
}
