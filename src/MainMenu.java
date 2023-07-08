import java.util.List;
import java.util.Scanner;

public class MainMenu {
    private Scanner input;
    private CurrentUser currentUser;
    public MainMenu(CurrentUser currentUser, List<User> userList) {
        input = new Scanner(System.in);
        this.currentUser = currentUser;
    }

    public void setCurrentUser(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    public void showMenu() {
        short option;
        boolean loop = true;

        while (loop) {
            displayMenu();
            option = getUserOption();

            switch (option) {
                case 1:
                    System.out.println("Chat Personal");
                    System.out.println("=============");
                    break;
                case 2:
                    System.out.println("Chat Group");
                    System.out.println("===========");
                    break;
                case 3:
                    System.out.println("Contact List");
                    System.out.println("=============");
                    currentUser.displayContactList();
                    break;
                case 4:
                    System.out.println("Add New Contact");
                    System.out.println("================");
                    // Implementasi logika untuk fitur komunitas
                    break;
                case 5:
                    System.out.println("Make New Group");
                    System.out.println("===============");
                    // Implementasi logika untuk fitur pengaturan
                    break;
                case 6:
                    System.out.println("Goodbye...");
                    loop = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.\n");
                    break;
            }
        }

        input.close();
    }

    private void displayMenu() {
        System.out.println();
        System.out.println("ChatApp");
        System.out.println("========");
        displayStatus();
        System.out.println("Chat Available:");
        //currentUser.displayReceivedChat();
        System.out.println("Show grouplist");
        System.out.println();
        System.out.println("Menu: ");
        System.out.println("1. Chat Personal");
        System.out.println("2. Chat Group");
        System.out.println("3. Show Contact");
        System.out.println("4. Add New Contact");
        System.out.println("5. Make new Group");
        System.out.println("6. Exit");
        System.out.print("Your option: ");
    }

    private short getUserOption() {
        short option = 0;
        boolean validOption = false;

        while (!validOption) {
            try {
                option = input.nextShort();
                input.nextLine();
                validOption = true;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid option.\n");
                input.nextLine();
            }
        }
        return option;
    }

    private void displayStatus() {
        currentUser.displayUserInfo();
    }

}
