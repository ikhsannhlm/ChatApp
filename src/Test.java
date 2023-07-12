import java.util.List;

public class Test {
	public static void main(String[] args) {
	
		List<Chat> chatList = DatabaseHandler.importChat();
		
		for (Chat c : chatList) {
			System.out.println(c);
		}
	
	}
	
}
