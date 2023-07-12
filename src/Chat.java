import java.util.ArrayList;
import java.util.List;

public class Chat {
	/*
	 * Base class for Chat, this handle participant and ID of Personal Chat.
	 * This class can be Extended for GroupChat by change both attribute to protected 
	 * and then add other attribute like GroupName or GroupParticipantLimit on the other class
	 */
	private int vChatID;
	private List<User> vChatParticipant;
	
	/*
	 * Constructor of Chat
	 * @param pChatID = ID assigned to corresponding chat between two User
	 * @param pChatParticpant = List of Participant for certain ID, 
	 * its max of 2 User for Personal and has currently no limit for group (not explicitly add the limit) 
	 */
	public Chat (int pChatID, List<User> pChatParticipant) {
		this.vChatID = pChatID;
		this.vChatParticipant = pChatParticipant;
	}
	
	/*
	 * Method used to retrieve ID of a Chat
	 * @return ID of Chat (vChatID)
	 */
	public int getChatID() {
		return vChatID;
	}
	
	/*
	 * Method used to retrieve list of participant Username of certain Chat
	 * @return List of participant Username (participantUsername)
	 */
	public List<String> getChatParticipantUsername(){
		List<String> participantUsername = new ArrayList<>();
		
		for (User u : vChatParticipant) {
			participantUsername.add(u.getUsername());
		}
		
		return participantUsername;
	}
	
	/*
	 * Method used to retrieve list of participant of certain Chat
	 * @return List of Participant (User)
	 */
	public List<User> getChatParticipant(){
		return vChatParticipant;
	}
	
	/*
	 * method to see Chat Detail.. (basically just for testing)
	 */
	public String toString() {
		return "ChatID: " + vChatID +
				"\nChatParticipant: " + getChatParticipantUsername();
	}
}
