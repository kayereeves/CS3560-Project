import java.io.IOException;
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

    /////////////////// View Schedule Filters

    public List<Task> getTasksByFilter(char filter, Date startDate) {
        List<Task> filteredTasks = new ArrayList<Task>();
        switch (filter) {
            case '1':
                // particular day
                System.out.println();
                System.out.println("Printing schedule for Day of " + startDate.getDateString() + ".");
                for (Task task : taskData) {
                    if (task.getClass() == RecurringTask.class) {
                        if (((RecurringTask) task).recurringDates.contains(startDate.getDateInt())) {
                            filteredTasks.add(task);
                        }
                    } else if (task.getDate().getDateInt() == startDate.getDateInt()) {
                        filteredTasks.add(task);
                    }
                }
                break;
            case '2':
                // particular week
                System.out.println();
                System.out.println("Printing schedule for Week of " + startDate.getDateString() + ".");
                for (Task task : taskData) {
                    int increment = 0;
                    for (int i = startDate.getDay() % 7; i <= 7; i++) {
                        Date dayOfWeek = new Date(startDate.getMonth(), startDate.getDay() + (increment++), startDate.getYear());
                        if (task.getClass() == RecurringTask.class) {
                            if (((RecurringTask) task).recurringDates.contains(dayOfWeek.getDateInt())) {
                                filteredTasks.add(task);
                                break;
                            }
                        } else if (task.getDate().getDateInt() == dayOfWeek.getDateInt()) {
                            filteredTasks.add(task);
                            break;
                        }
                    }
                }
                break;
            case '3':
                // particular month
                System.out.println();
                System.out.println("Printing schedule for Month of " + startDate.getDateString() + ".");
                for (Task task : taskData) {
                    int increment = 0;
                    for (int i = startDate.getDay() % startDate.getDaysInMonth(); i <= startDate.getDaysInMonth(); i++) {
                        Date dayOfMonth = new Date(startDate.getMonth(), startDate.getDay() + (increment++), startDate.getYear());
                        if (task.getClass() == RecurringTask.class) {
                            if (((RecurringTask) task).recurringDates.contains(dayOfMonth.getDateInt())) {
                                filteredTasks.add(task);
                                break;
                            }
                        } else if (task.getDate().getDateInt() == dayOfMonth.getDateInt()) {
                            filteredTasks.add(task);
                            break;
                        }
                    }
                }
                break;
            default:
                System.out.print("Invalid input.");
        }
        return filteredTasks;
    }

    /////////////////// End View Schedule Filters


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
                if (noOverlap(transientData, "transienttask")) {
                    taskData.add(
                            new TransientTask(
                                    String.valueOf(transientData[0]),
                                    (String[]) transientData[1],
                                    (Time) transientData[2],
                                    (Time) transientData[3],
                                    (Date) transientData[4]
                            ));
                } else {
                    view.displayCreateTaskError();
                    return;
                }
                break;
            case "recurringtask":
                Object[] recurringData = view.retrieveTaskData(type, null);
                if (noOverlap(recurringData, "recurringtask")) {
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
                } else {
                    view.displayCreateTaskError();
                    return;
                }
                break;
            case "antitask":
                Object[] antitaskData = view.retrieveTaskData(type, null);
                if (validAntiTask(antitaskData) && noOverlap(antitaskData, "antitask")) {
                    taskData.add(
                            new AntiTask(
                                    String.valueOf(antitaskData[0]),
                                    (String[]) antitaskData[1],
                                    (Time) antitaskData[2],
                                    (Time) antitaskData[3],
                                    (Date) antitaskData[4]
                            ));
                } else {
                    view.displayCreateTaskError();
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

    public boolean noOverlap(Object[] inputTask, String type) {
        for (Task taskDatum : taskData) {
            if (type.equals("transienttask")) {
                if (transientTaskIsOverlap(taskDatum, inputTask)) return false;
            } else if (type.equals("recurringtask")) {
                if (recurringTaskIsOverlap(taskDatum, inputTask)) return false;
            } else if (type.equals("antitask")) {
                if (antiTaskIsOverlap(taskDatum, inputTask)) return false;
            } else {
                return false;
            }
        }
        return true;
    }

    // Returns true if transient task overlaps with recurring or other transient
    public boolean transientTaskIsOverlap(Task taskDatum, Object[] inputTask) {
        Date inputDate = (Date) inputTask[4];
        Time startInputTime = (Time) inputTask[2];
        Time endInputTime = (Time) inputTask[3];

        // check if overlapping a transient task
        if (taskDatum.getClass().equals(TransientTask.class)) {
            if (taskDatum.getDate().getDateString().equals(inputDate.getDateString())) {
                if (startInputTime.getTimeDouble() <= taskDatum.getEndTime().getTimeDouble()
                        && startInputTime.getTimeDouble() >= taskDatum.getEndTime().getTimeDouble()
                        || endInputTime.getTimeDouble() <= taskDatum.getEndTime().getTimeDouble()
                        && endInputTime.getTimeDouble() >= taskDatum.getStartTime().getTimeDouble()) {
                    return true;
                }
            }

            // check if overlapping a recurring task
        } else if (taskDatum.getClass().equals(RecurringTask.class)) {
            if (startInputTime.getTimeDouble() <= taskDatum.getEndTime().getTimeDouble()
                    && startInputTime.getTimeDouble() >= taskDatum.getEndTime().getTimeDouble()
                    || endInputTime.getTimeDouble() <= taskDatum.getEndTime().getTimeDouble()
                    && endInputTime.getTimeDouble() >= taskDatum.getStartTime().getTimeDouble()) {
                for (int recurringDate : ((RecurringTask) taskDatum).recurringDates) {
                    if (inputDate.getDateInt() == recurringDate) {
                        for (Task antiTask : taskData) {
                            if (antiTask.getClass().equals(AntiTask.class)
                                    && antiTask.getDate().getDateInt() == recurringDate
                                    && antiTask.getStartTime().getTimeDouble() == taskDatum.getStartTime().getTimeDouble()) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }


    // Returns true if recurring task overlaps with transient or other recurring
    public boolean recurringTaskIsOverlap(Task taskDatum, Object[] inputTask) {
        RecurringTask recurringTask = new RecurringTask(
                String.valueOf(inputTask[0]),
                (String[]) inputTask[1],
                (Time) inputTask[2],
                (Time) inputTask[3],
                (Date) inputTask[4],
                (Date) inputTask[5],
                (int) inputTask[6]);

        // check if overlapping a transient task
        if (taskDatum.getClass().equals(TransientTask.class)) {
            if (recurringTask.getStartTime().getTimeDouble() <= taskDatum.getEndTime().getTimeDouble()
                    && recurringTask.getStartTime().getTimeDouble() >= taskDatum.getEndTime().getTimeDouble()
                    || recurringTask.getEndTime().getTimeDouble() <= taskDatum.getEndTime().getTimeDouble()
                    && recurringTask.getEndTime().getTimeDouble() >= taskDatum.getStartTime().getTimeDouble()) {
                for (int recurringDate : recurringTask.recurringDates) {
                    if (taskDatum.getDate().getDateInt() == recurringDate) {
                        return true;
                    }
                }
            }
        }
        // check if overlapping a recurring task
        else if (taskDatum.getClass().equals(RecurringTask.class)) {
            if (recurringTask.getStartTime().getTimeDouble() <= taskDatum.getEndTime().getTimeDouble()
                    && recurringTask.getStartTime().getTimeDouble() >= taskDatum.getEndTime().getTimeDouble()
                    || recurringTask.getEndTime().getTimeDouble() <= taskDatum.getEndTime().getTimeDouble()
                    && recurringTask.getEndTime().getTimeDouble() >= taskDatum.getStartTime().getTimeDouble()) {
                for (int inputRecurringDate : recurringTask.recurringDates) {
                    for (int existingRecurringDate : ((RecurringTask) taskDatum).recurringDates) {
                        if (existingRecurringDate == inputRecurringDate) {
                            for (Task antiTask : taskData) {
                                if (antiTask.getClass().equals(AntiTask.class)
                                        && antiTask.getDate().getDateInt() == existingRecurringDate
                                        && antiTask.getStartTime().getTimeDouble() == taskDatum.getStartTime().getTimeDouble()) {
                                    return false;
                                }
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // Returns true if antitask overlaps with another antitask
    public boolean antiTaskIsOverlap(Task taskDatum, Object[] inputTask) {
        if (taskDatum.getClass().equals(AntiTask.class)) {
            Date inputDate = (Date) inputTask[4];
            if (taskDatum.getDate().getDateString().equals(inputDate.getDateString())) {
                Time inputTime = (Time) inputTask[2];
                return taskDatum.getStartTime().getTimeString().equals(inputTime.getTimeString());
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

    public void readFile() throws IOException {
        System.out.print("Enter file to retrieve data: ");
        String fileName = this.view.getScanner().next();
        fileIO.readFile(this.taskData, fileName);
    }
}
