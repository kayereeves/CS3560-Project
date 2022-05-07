import javax.swing.event.SwingPropertyChangeSupport;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        char response = 'a';
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to CS3560 PSS! Enter a response at the prompt or press CTRL + X + C to quit.");
            System.out.println();
            System.out.println("1: View Schedule");
            System.out.println("2: Edit Schedule");
            System.out.println("3: Write To File");
            System.out.println("4: Read From File");
            System.out.println();

            try {
                response = sc.next().charAt(0);
            }
            catch (NoSuchElementException e) {
                System.out.println();
                System.out.println("Goodbye!");
                break;
            }

            switch(response) {
                case '1':
                    //display schedule
                    System.out.println();
                    System.out.println("View Schedule");
                    break;
                
                case '2':
                    //edit schedule
                    System.out.println();
                    System.out.println("Edit Schedule"); 
                    break;

                case '3':
                    //write schedule to file
                    System.out.println();
                    System.out.println("Write To File");
                    break;

                case '4':
                    //read schedule from file
                    System.out.println();
                    System.out.println("Read From File");
                    break;

                default:
                    System.out.println("Invalid response. Please enter a valid response.");
                    break;
            }
            System.out.println();
        }
    }
}
