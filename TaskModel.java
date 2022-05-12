import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TaskModel {
    private List<Task> taskData = new ArrayList<Task>();
    private FileIO fileIO;
    private TaskView view;

    public TaskModel() {
        this.fileIO = new FileIO();
        this.view = new TaskView();
    }

    public List<Task> getTasks() {
        return this.taskData;
    }

    public List<Task> getTasksByDate(Date date) {
        List<Task> tasks = new ArrayList<Task>();
        this.taskData.forEach((task) -> {
            if (task.getDate().getDateString().equals(date.getDateString())) {
                tasks.add(task);
            }

            if (task.getType().equals("recurringtask")) {
                //do something for recurring tasks
            }
        });

        return tasks;
    }

    public List<Task> getTasksByDate(Date date, Time start, Time end) {
        List<Task> tasks = new ArrayList<Task>();
        this.taskData.forEach((task) -> {
            if (task.getDate().getDateString().equals(date.getDateString())) {
                tasks.add(task);
            }

            if (task.getType().equals("recurringtask")) {
                //do something for recurring tasks
            }
        });

        return tasks;
    }

    public FileIO getFileIO() {
        return this.fileIO;
    }

    public TaskView getView() {
        return this.view;
    }

    public void removeTask(String taskName) {
        if (this.taskData.removeIf(task -> task.getName().equals(taskName))) {
            System.out.println("Task \"" + taskName + "\" successfully removed.");
        } else {
            System.out.println("Task \"" + taskName + "\" not found.");
        }
        System.out.println();
    }

    public void createTask(String type, TaskView view) {

        Scanner s = view.getScanner();

        switch (type) {
            case "transienttask":
                Object[] transientData = view.retrieveTaskData(type);
                taskData.add(
                        new TransientTask(
                                String.valueOf(transientData[0]),
                                String.valueOf(transientData[1]),
                                (Time) transientData[2],
                                (Time) transientData[3],
                                (Date) transientData[4]
                        ));
                break;
            case "recurringtask":
                Object[] recurringData = view.retrieveTaskData(type);
                taskData.add(
                        new RecurringTask(
                                String.valueOf(recurringData[0]),
                                String.valueOf(recurringData[1]),
                                (Time) recurringData[2],
                                (Time) recurringData[3],
                                (Date) recurringData[4],
                                (Date) recurringData[5],
                                (int) recurringData[6]
                        ));
                break;
            case "antitask":
                Object[] antitaskData = view.retrieveTaskData(type);
                taskData.add(
                        new AntiTask(
                                String.valueOf(antitaskData[0]),
                                String.valueOf(antitaskData[1]),
                                (Time) antitaskData[2],
                                (Time) antitaskData[3],
                                (Date) antitaskData[4]
                        ));
                break;
            default:
                System.out.println("Could not create task.");
        }

        System.out.println("Task added to schedule.");
        System.out.println();
    }

    public void editTask(String taskName) {
        if (this.taskData.contains(taskName)) {
            // Edit task
        } else {
            System.out.println("Task \"" + taskName + "\" not found.");
        }
        System.out.println();
    }

    public void writeFile() {
        System.out.print("Enter file to write data: ");
        String fileName = "";

        while (true) {
            try {
                fileName = this.view.getScanner().next("([0-9]?[a-z]?[A-Z]?)*.json");
                break;
            } catch (InputMismatchException e) {
                System.out.print("Input error. Check format and try again: ");
                this.view.getScanner().next();
            }
        }

        fileIO.writeFile(this.taskData, fileName);
    }

    public void readFile() {
        System.out.print("Enter file to retrieve data: ");
        String fileName = "";

        while (true) {
            try {
                fileName = this.view.getScanner().next("([0-9]?[a-z]?[A-Z]?)*.json");
                break;
            } catch (InputMismatchException e) {
                System.out.print("Input error. Check format and try again: ");
                this.view.getScanner().next();
            }
        }

        fileIO.readFile(this.taskData, fileName);
    }
}
