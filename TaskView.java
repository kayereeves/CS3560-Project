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
    public char displayTaskSubtypePrompt(int taskType) {
        Scanner sc = getScanner();
        char response = ' ';
        switch (taskType) {
            case 1:
                System.out.println("Please choose a transient task subtype: ");
                System.out.println("1. Visit");
                System.out.println("2. Shopping");
                System.out.println("3. Appointment");
                response = sc.next().charAt(0);
                break;
            case 2:
                System.out.println("Please choose a recurring task subtype: ");
                System.out.println("1. Class");
                System.out.println("2. Study");
                System.out.println("3. Sleep");
                System.out.println("4. Exercise");
                System.out.println("5. Work");
                System.out.println("6. Meal");
                response = sc.next().charAt(0);
                break;
            default:
                System.out.println("An error has occurred with selecting subtypes.");
        }

        return response;
    }

    //For create task
    public char displayTaskSelectionPrompt() {
        System.out.println("What would you like to do?");
        System.out.println("1. Create Task");
        System.out.println("2. Delete Task");
        System.out.println("3: Edit Task");

        Scanner sc = getScanner();
        char response = sc.next().charAt(0);

        return response;
    }

    public Object[] retrieveTaskData(String[] taskType, Task task) {
        Scanner sc = getScanner();
        Object[] arguments = null;
        String name;
        Time startTime;
        Time endTime;
        Date date;

        /*
        if (taskType[0].equals("antitask")) {
            System.out.println();
            System.out.println("Please enter recurring task name to cancel out:");
            System.out.println();

            String recurringTaskName = sc.next();
            while () {
                System.out.println("Invalid time");
                recurringTaskName = sc.next();
            }
        }*/

        System.out.println();
        System.out.println("Please enter task data:");
        System.out.println();
        System.out.print("Task name (no spaces, please): ");
        if (task != null) System.out.println("\nPrevious Name: " + task.getName());
        name = sc.next();
        //check if name exists

        System.out.print("Please enter start time (hh:mm, 24-hour clock): ");
        if (task != null) System.out.println("\nPrevious Start Time: " + task.getStartTime().getTimeString());
        startTime = Time.parseTime(sc);
        while (!startTime.validate()) {
            System.out.println("Invalid time");
            startTime = Time.parseTime(sc);
        }
        System.out.print("Please enter end time (hh:mm, 24-hour clock): ");
        if (task != null) System.out.println("\nPrevious End Time: " + task.getEndTime().getTimeString());
        endTime = Time.parseTime(sc);
        while (!endTime.validate()) {
            System.out.println("Invalid time");
            endTime = Time.parseTime(sc);
        }

        //check if valid or overlap

        if (taskType[0].equals("transienttask") || taskType[0].equals("antitask")) {
            System.out.print("What date will this task occur? (mm/dd/yyyy): ");
            if (task != null) System.out.println("\nPrevious Date: " + task.getDate().getDateString());
            date = Date.parseDate(sc);
            while (!date.validate()) {
                System.out.println("Invalid calendar date");
                date = Date.parseDate(sc);
            }
            //check if valid or if task with this name exists on this day already??
            //maybe it should go before task name then? bc a task might have same name but be on different days
            arguments = new Object[]{name, taskType, startTime, endTime, date};
        } else if (taskType[0].equals("recurringtask")) {
            Date endDate;
            int frequency;

            System.out.print("What date will this task begin? (mm/dd/yyyy): ");
            if (task != null) System.out.println("\nPrevious Start Date: " + task.getDate().getDateString());
            date = Date.parseDate(sc);
            while (!date.validate()) {
                System.out.println("Invalid calendar date");
                date = Date.parseDate(sc);
            }
            //check if valid
            System.out.print("What date will this task end? (mm/dd/yyyy): ");
            if (task != null)
                System.out.println("\nPrevious End Date: " + ((RecurringTask) task).getEndDate().getDateString());
            endDate = Date.parseDate(sc);
            while (!endDate.validate()) {
                System.out.println("Invalid calendar date");
                endDate = Date.parseDate(sc);
            }
            // System.out.println("Valid end date? " + endDate.validEndDate(date));

            System.out.print("How many times will it occur? (1 or 7): ");
            if (task != null) System.out.println("\nPrevious Frequency: " + ((RecurringTask) task).getFrequency());
            frequency = sc.nextInt();
            while (frequency != 1 && frequency != 7) {
                System.out.println("Invalid frequency.");
                frequency = sc.nextInt();
            }
            System.out.println();

            arguments = new Object[]{name, taskType, startTime, endTime, date, endDate, frequency};
        }

        System.out.println();
        return arguments;
    }

}
