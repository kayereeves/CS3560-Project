import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class TaskView {

    private Scanner sc = new Scanner(System.in);

    public Scanner getScanner() {
        return sc;
    }

    //TODO: add a way to list tasks by date or date range
    public void displaySchedule(List<Task> tasks) {
        System.out.println();
        System.out.println("---Schedule---");
        System.out.println();

        if (tasks.size() == 0) {
            System.out.println("There are no tasks.");
        } else {
            tasks.forEach((task) -> displayTask(task));
        }
        System.out.println();

        /* TODO: Add formatting for schedule display and different
            display types
         */
    }

    public void displayTask(Task task) {
        task.print();
        System.out.println();
    }

    public char displayMenu() {
        char response = 'a';
        Scanner sc = getScanner();

        System.out.println("Welcome to CS3560 PSS! Enter a response at the prompt or press Q to quit.");
        System.out.println();
        System.out.println("1: View Schedule");
        System.out.println("2: Edit Schedule");
        System.out.println("3: Write To File");
        System.out.println("4: Read From File");

        try {
            response = sc.next().charAt(0);
        } catch (NoSuchElementException e) {
            System.out.println();
            System.exit(0);
        }

        System.out.println();
        return response;
    }

    public void displayInvalidResponse() {
        System.out.println("Invalid response. Please enter a valid response. \n");
    }

    //For create task
    public char displayTaskTypePrompt() {
        System.out.println("Please choose a task type: ");
        System.out.println("1. Transient Task");
        System.out.println("2. Recurring Task");
        System.out.println("3. AntiTask      ");

        Scanner sc = getScanner();
        char response = sc.next().charAt(0);

        return response;
    }

    //For create task
    public char displayTaskSelectionPrompt() {
        System.out.println("What would you like to do?");
        System.out.println("1. Create Task");
        System.out.println("2. Delete Task");

        Scanner sc = getScanner();
        char response = sc.next().charAt(0);

        return response;
    }

    public Object[] retrieveTaskData(String taskType) {
        Scanner sc = getScanner();
        Object[] arguments = null;
        String name;
        Time startTime;
        Time endTime;
        Date date;

        System.out.println();
        System.out.println("Please enter task data:");
        System.out.println();
        System.out.print("Task name (no spaces, please): ");
        name = sc.next();
        //check if name exists

        System.out.print("Please enter start time (hh:mm, 24-hour clock): ");
        startTime = Time.parseTime(sc);
        while (!startTime.validate()) {
            System.out.println("Invalid time");
            startTime = Time.parseTime(sc);
        }
        System.out.print("Please enter end time (hh:mm, 24-hour clock): ");
        endTime = Time.parseTime(sc);
        while (!endTime.validate() || !endTime.validEndTime(startTime)) {
            System.out.println("Invalid time");
            endTime = Time.parseTime(sc);
        }

        //check if valid or overlap

        if (taskType.equals("transienttask") || taskType.equals("antitask")) {
            System.out.print("What date will this task occur? (mm/dd/yyyy): ");
            date = Date.parseDate(sc);
            while (!date.validate()) {
                System.out.println("Invalid calendar date");
                date = Date.parseDate(sc);
            }
            //check if valid or if task with this name exists on this day already??
            //maybe it should go before task name then? bc a task might have same name but be on different days
            arguments = new Object[]{name, taskType, startTime, endTime, date};
        } else if (taskType.equals("recurringtask")) {
            Date endDate;
            int frequency;

            System.out.print("What date will this task begin? (mm/dd/yyyy): ");
            date = Date.parseDate(sc);
            while (!date.validate()) {
                System.out.println("Invalid calendar date");
                date = Date.parseDate(sc);
            }
            //check if valid
            System.out.print("What date will this task end? (mm/dd/yyyy): ");
            endDate = Date.parseDate(sc);
            while (!endDate.validate() || !endDate.validEndDate(date)) {
                System.out.println("Invalid calendar date");
                endDate = Date.parseDate(sc);
            }
            // System.out.println("Valid end date? " + endDate.validEndDate(date));

            System.out.print("How many times will it occur? (enter number only): ");
            frequency = sc.nextInt();
            System.out.println();

            arguments = new Object[]{name, taskType, startTime, endTime, date, endDate, frequency};
        }

        System.out.println();
        return arguments;
    }

}
