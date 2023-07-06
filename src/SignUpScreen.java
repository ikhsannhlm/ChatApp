import java.util.List;
import java.util.Scanner;

public class SignUpScreen {
    private static Scanner input= new Scanner(System.in);

    public static void signUp() {
        System.out.println();
        System.out.println("Sign Up ");
        System.out.println("========");
        System.out.print("Enter your username: ");
        String username = input.nextLine();
        System.out.print("Enter your number: ");
        String number = input.nextLine();
        User.createUser(username, number);

    }
}
