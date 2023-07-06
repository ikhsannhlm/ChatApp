import java.util.Scanner;

public class GlobalScanner {
	private static Scanner sc = new Scanner(System.in);;
	
	private GlobalScanner() {
	}
	
	public static Scanner getScanner() {
		return sc;
	}
	
}
