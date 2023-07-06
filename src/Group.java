import java.util.ArrayList;
import java.util.List;

public class Group {
    private String groupName;
    private List<User> members;
    private List<Message> groupChatList;

    // Konstruktor
    public Group(String groupName) {
        this.groupName = groupName;
        this.members = new ArrayList<>();
        this.groupChatList = new ArrayList<>();
    }
    
    // Mendapatkan Nama Grup
    public String getGroupName() {
        return groupName;
    }

    // Menambahkan Anggota
    public void addMember(User user) {
        members.add(user);
    }

    // Menghitung Anggota Grup
    public int getMemberCount() {
        return members.size();
    }

    // Mendapatkan Daftar Anggota
    public List<User> getMembers() {
        return members;
    }

    // Menampilkan Daftar Anggota Grup
    public void displayMembers() {
        System.out.println("Members of Group " + groupName + ":");
        for (User member : members) {
            System.out.println(member.getUsername());
        }
        System.out.println();
    }

    // Menambahkan Pesan kedalam List Chat
    public void addMessageToChatList(Message message) {
        groupChatList.add(message);
    }

    // Menerima Pesan lalu menambahkan nya ke dalam List
    public void receiveMessage(Message message) {
        groupChatList.add(message);
    }

    // Mendapatkan List Chat grup
    public List<Message> getGroupChatList() {
        return groupChatList;
    }

    // Menampilkan List Chat Grup
    public void displayChatList() {
        System.out.println("Chat List for Group " + groupName);
        for (Message message : groupChatList) {
            System.out.println(message);
        }
        System.out.println();
    }
}
