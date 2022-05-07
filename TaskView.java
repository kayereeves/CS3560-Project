import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TaskView {

    public void displaySchedule(List<Task> tasks) {
        System.out.print("Schedule: ");
        /* TODO: Add formatting for schedule display and different
            display types
         */
    }

    public void displayTask(Task task) {
        System.out.print("Task: ");
        // TODO: Add formatting for displaying different tasks
    }

    public char displayMenu() {
        char response = 'a';
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to CS3560 PSS! Enter a response at the prompt or press CTRL + X + C to quit.");
        System.out.println();
        System.out.println("1: View Schedule");
        System.out.println("2: Edit Schedule");
        System.out.println("3: Write To File");
        System.out.println("4: Read From File");
        System.out.println();

        try {
            response = sc.next().charAt(0);
        } catch (NoSuchElementException e) {
            System.out.println();
            System.out.println("Goodbye!");
        }

        return response;
    }

    public void displayViewSchedule() {
        System.out.println("View Schedule \n");
    }

    public void displayEditSchedule() {
        System.out.println("Edit Schedule \n");
    }

    public void displayWriteFile() {
        System.out.println("Write To File \n");
    }

    public void displayReadFile() {
        System.out.println("Read From File \n");
    }

    public void displayInvalidResponse() {
        System.out.println("Invalid response. Please enter a valid response. \n");
    }
}
