public class SignUpScreen {

    public static boolean signUp() {
    	
    	boolean result = false;
        System.out.println();
        System.out.println("Sign Up ");
        System.out.println("========");
        System.out.print("Enter your username: ");
        String username = GlobalScanner.getScanner().nextLine();
        System.out.print("Enter your number: ");
        String number = GlobalScanner.getScanner().nextLine();
        result = DatabaseHandler.createUser(username, number);
        return result;
        
    }
    
}
