import java.util.ArrayList;
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
        boolean removeSuccess = this.taskData.removeIf(task -> task.getName().equals(taskName));
        if (removeSuccess) {
            System.out.println("Task \"" + taskName + "\" successfully removed.");
            System.out.println();
        } else {
            System.out.println("Task \"" + taskName + "\" does not exist.");
            System.out.println();
        }
    }

    public void createTask(String[] type, TaskView view) {

        Scanner s = view.getScanner();

        switch (type[0]) {
            case "transienttask":
                Object[] transientData = view.retrieveTaskData(type, null);
                taskData.add(
                        new TransientTask(
                                String.valueOf(transientData[0]),
                                (String[]) transientData[1],
                                (Time) transientData[2],
                                (Time) transientData[3],
                                (Date) transientData[4]
                        ));
                break;
            case "recurringtask":
                Object[] recurringData = view.retrieveTaskData(type, null);
                taskData.add(
                        new RecurringTask(
                                String.valueOf(recurringData[0]),
                                (String[]) recurringData[1],
                                (Time) recurringData[2],
                                (Time) recurringData[3],
                                (Date) recurringData[4],
                                (Date) recurringData[5],
                                (int) recurringData[6]
                        ));
                break;
            case "antitask":
                Object[] antitaskData = view.retrieveTaskData(type, null);
                if (validAntiTask(antitaskData)) {
                    taskData.add(
                            new AntiTask(
                                    String.valueOf(antitaskData[0]),
                                    (String[]) antitaskData[1],
                                    (Time) antitaskData[2],
                                    (Time) antitaskData[3],
                                    (Date) antitaskData[4]
                            ));
                } else {
                    System.out.println("Failed to create task. Invalid values.");
                    System.out.println();
                    return;
                }
                break;
            default:
                System.out.println("Could not create task.");
        }

        System.out.println("Task added to schedule.");
        System.out.println();
    }

    public boolean validAntiTask(Object[] antiTaskData) {
        Time startTime = (Time) antiTaskData[2];
        Time endTime = (Time) antiTaskData[3];
        Date date = (Date) antiTaskData[4];

        for (Task taskDatum : taskData) {
            if (taskDatum.getClass().equals(RecurringTask.class)) {
                RecurringTask recurringTask = (RecurringTask) taskDatum;
                if (recurringTask.recurringDates.contains(date.getDateInt())
                        && recurringTask.getStartTime().getTimeDouble() == startTime.getTimeDouble()
                        && recurringTask.getEndTime().getTimeDouble() == endTime.getTimeDouble()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void editTask(String taskName) {
        if (taskData.isEmpty()) {
            System.out.println("No tasks to edit.");
            System.out.println(2);
            return;
        }
        taskData.forEach(task -> {
            if (task.getName().equals(taskName)) {
                int index = taskData.indexOf(task);
                String[] type = task.getType();
                switch (type[0]) {
                    case "recurringtask":
                        Object[] recurringData = view.retrieveTaskData(type, task);
                        taskData.set(index,
                                new RecurringTask(
                                        String.valueOf(recurringData[0]),
                                        (String[]) recurringData[1],
                                        (Time) recurringData[2],
                                        (Time) recurringData[3],
                                        (Date) recurringData[4],
                                        (Date) recurringData[5],
                                        (int) recurringData[6]
                                ));
                        break;
                    case "transienttask":
                        Object[] transientData = view.retrieveTaskData(type, task);
                        taskData.set(index,
                                new TransientTask(
                                        String.valueOf(transientData[0]),
                                        (String[]) transientData[1],
                                        (Time) transientData[2],
                                        (Time) transientData[3],
                                        (Date) transientData[4]
                                ));
                        break;
                    case "antitask":
                        Object[] antitaskData = view.retrieveTaskData(type, task);
                        if (validAntiTask(antitaskData)) {
                            taskData.set(index,
                                    new AntiTask(
                                            String.valueOf(antitaskData[0]),
                                            (String[]) antitaskData[1],
                                            (Time) antitaskData[2],
                                            (Time) antitaskData[3],
                                            (Date) antitaskData[4]
                                    ));
                        } else {
                            System.out.println("Failed to edit task. Invalid values.");
                            System.out.println();
                            return;
                        }
                        break;
                    default:
                        System.out.println("Could not edit task.");
                }
                return;
            }
        });
        //TODO: make it so this code doesn't always print not found.
        System.out.println("Task \"" + taskName + "\" not found.");
        System.out.println();
    }

    public void writeFile() {
        System.out.print("Enter file to write data: ");
        String fileName = this.view.getScanner().next();
        fileIO.writeFile(this.taskData, fileName);
    }

    public void readFile() {
        System.out.print("Enter file to retrieve data: ");
        String fileName = this.view.getScanner().next();
        fileIO.readFile(this.taskData, fileName);
    }
}
