import java.util.*;

public class TaskModel {
    private List<Task> taskData = new ArrayList<Task>();
    private FileIO fileIO;
    private TaskView view;

    public TaskModel() {
        this.fileIO = new FileIO();
        this.view = new TaskView();
    }

    public List<Task> getTasks() {
        return taskData;
    }

    public FileIO getFileIO() {
    	return fileIO;
    }
    
    public TaskView getView() {
    	return view;
    }

    public void createTask(String type, TaskView view) {

        Scanner s = view.getScanner();
        
        switch(type){
            case "transienttask":
            Object[] transientData = view.retrieveTaskData(type);
            taskData.add(
                    new TransientTask(
                         String.valueOf(transientData[0]),
                         String.valueOf(transientData[1]),
                         (Time)transientData[2],
                         (Time)transientData[3],
                         (Date)transientData[4]
                         ));
            break;
            case "recurringtask":
            Object[] recurringData = view.retrieveTaskData(type);
            taskData.add(
                        new RecurringTask(
                            String.valueOf(recurringData[0]),
                            String.valueOf(recurringData[1]),
                            (Time)recurringData[2],
                            (Time)recurringData[3],
                            (Date)recurringData[4],
                            (Date)recurringData[5],
                            (int)recurringData[6]
                        ));
            break;
            case "antitask":
            Object[] antitaskData = view.retrieveTaskData(type);
            taskData.add(
                    new AntiTask(
                        String.valueOf(antitaskData[0]),
                        String.valueOf(antitaskData[1]),
                        (Time)antitaskData[2],
                        (Time)antitaskData[3],
                        (Date)antitaskData[4]
                    ));
            break;
            default:
            System.out.println("Could not create task.");
        }

        System.out.println("Task added to schedule.");
        System.out.println();
    }

    public void editTask() {
        // TODO: Edit task method
        // taskData.add(task);
    }

    public void deleteTask() {
        // TODO: Delete task method
        // taskData.remove(task)
    }

    public void writeFile() {
        // change output.txt to user input
        fileIO.writeFile(this.taskData, "output.txt");
    }

    public void readFile() {
        // change output.txt to user input
        fileIO.readFile(this.taskData, "output.txt");
    }
}