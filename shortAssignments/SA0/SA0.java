/**
 * Program says "Hello World", prints date and time, and takes input to return a specified number of the fibonacci sequence
 * @author Alex Craig
 */

package SA0;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class SA0 {

    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd, HH:mm");
    private static LocalDateTime currTime = LocalDateTime.now();

    // Prints Hello World and the current date and time
    public static void printStatement() {
        String dateAndTime = dtf.format(currTime);
        System.out.println("\n\tHello World!\n\n\tThe Current Date and Time is: " + dateAndTime);
    }

    // Returns the nth number in the fibonacci sequence
    public static int fibonacci(Integer n) {
        if (n <= 1) {
            return n;
        } else {
            return fibonacci(n-1) + fibonacci(n-2);
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        SA0.printStatement();

        System.out.println("\n\tWhat number of the fibonacci sequence would you like to print?\n\t");
        Integer input = Integer.valueOf(scan.nextLine());
        System.out.println("\n\t" + SA0.fibonacci(input));
    }
}