
public class WelcomeScreen {

    public static String getOption() {

        System.out.println();
        System.out.println("Welcome to ChatApp");
        System.out.print("Do you have an Account? (y/n): ");
        String choice = GlobalScanner.getScanner().nextLine(); 
        return choice;
        
    }
}
