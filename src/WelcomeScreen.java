import java.util.Scanner;

public class WelcomeScreen {
    private Scanner input;

    public WelcomeScreen() {
        input = new Scanner(System.in);
    }

    public String getOption() {
        System.out.println();
        System.out.println("Welcome to ChatApp");
        System.out.print("Do you have an Account? (y/n): ");
        return input.nextLine();
    }
}
