import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TaskView {

    private Scanner sc = new Scanner(System.in);

    public Scanner getScanner(){
        return sc;
    }

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
        Scanner sc = getScanner();

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

    //For create task
    public char displayTaskPrompt() {
        System.out.println("Please choose a task type: ");
        System.out.println("1. Transient Task");
        System.out.println("2. Recurring Task");
        System.out.println("3. AntiTask      ");

        Scanner sc = getScanner();
        char response = sc.next().charAt(0);

        return response;
    }

    public Object[] retrieveTaskData(String taskType){
        Scanner sc = getScanner();
        Object[] arguments;
        String name;
        String startTime;   //parsed to float later
        String duration;    //parsed to float later
        int date;

        System.out.println("Please enter task data when prompted:");

        System.out.println("Task name:");
        name = sc.nextLine();
        //check if name exists

        System.out.println("Please enter start time (format: i forgot");
        startTime = sc.nextLine();
        //check if valid or overlap

        System.out.println("Please enter the duration:");
        duration = sc.nextLine();
        //check if valid or overlap

        if(taskType == "transient"){
            System.out.println("What date will this task occur?");
            date = sc.nextInt();
            //check if valid or if task with this name exists on this day already??
            //maybe it should go before task name then? bc a task might have same name but be on different days
            arguments = new Object[] {name, taskType, startTime, duration, date};
        }
        else if(taskType == "recurring"){
            int endDate;
            int frequency;

            System.out.println("What date will this task begin?");
            date = sc.nextInt();
            //check if valid
            System.out.println("What date will this task end?");
            endDate = sc.nextInt();
            //check if valid
            System.out.println("How many times will it occur? (enter number only)");
            frequency = sc.nextInt();

            arguments = new Object[] {name, taskType, startTime, duration, date, endDate, frequency};
        }

    //not sure how to fix this error
    return arguments;
    }

}
