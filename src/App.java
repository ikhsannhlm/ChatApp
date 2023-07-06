import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class App {
    private WelcomeScreen welcomeScreen;
    private SignInScreen signInScreen;
    private SignUpScreen signUpScreen;
    private MainMenu menu;
    private CurrentUser currentUser;
    private List<User> userList;

    public App() {
        welcomeScreen = new WelcomeScreen();
        userList = new ArrayList<>();
        signInScreen = new SignInScreen(userList);
        signUpScreen = new SignUpScreen(userList);
        currentUser = null;
        menu = new MainMenu(currentUser, userList);
    }

    public void startApp() {
        String choice = welcomeScreen.getOption();

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
        currentUser = signUpScreen.signUp();

        if (currentUser != null) {
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
