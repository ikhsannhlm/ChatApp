import java.util.ArrayList;
import java.util.List;

public class App {
    private SignInScreen signInScreen;
    private MainMenu menu;
    private CurrentUser currentUser;
    private List<User> userList;

    public App() {
        userList = new ArrayList<>();
        signInScreen = new SignInScreen();
        currentUser = null;
        menu = new MainMenu(currentUser, userList);
    }

    public void startApp() {
        String choice = WelcomeScreen.getOption();

        if (choice.equalsIgnoreCase("y")) {
            signIn();
        } else if (choice.equalsIgnoreCase("n")) {
            signUp();
        } else {
            System.out.println("Invalid input, please try again.\n");
            startApp();
        }
    }

    public void signIn() {
        currentUser = signInScreen.signIn();

        if (currentUser != null) {
            System.out.println("Sign In Success..");
            menu();
        } else {
            System.out.println("Sign In Failed..");
            startApp();
        }
    }

    public void signUp() {
    	
        boolean signUp = SignUpScreen.signUp();

        if (signUp == true) {
            System.out.println("Sign Up Success..");
            startApp();
        } else {
            System.out.println("Sign Up Failed..");
            startApp();
        }
        
    }

    public void menu() {
        menu.setCurrentUser(currentUser); // Mengatur currentUser pada MainMenu
        menu.showMenu();

    }

    public static void main(String[] args) {

    	App app = new App();
        app.startApp();

    }
}
