import java.util.*;

public class TaskModel {
    private List<Task> taskData = new ArrayList<Task>();
    private FileIO fileIO;
    private TaskView view;

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
            case "transient":

            Object[] transientData = view.retrieveTaskData(type);
            taskData.add(
                    new TransientTask(
                         String.valueOf(transientData[0]),
                         String.valueOf(transientData[1]),
                         (Time)transientData[2],
                         (Time)transientData[3],
                         (double)transientData[4],
                         (Date)transientData[5]
                         ));
            break;
            case "recurring":
            Object[] recurringData = view.retrieveTaskData(type);
            taskData.add(
                        new RecurringTask(
                            String.valueOf(recurringData[0]),
                            String.valueOf(recurringData[1]),
                            (Time)recurringData[2],
                            (Time)recurringData[3],
                            (double)recurringData[4],
                            (Date)recurringData[5],
                            (Date)recurringData[6],
                            (int)recurringData[7]
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
                        (double)antitaskData[4],
                        (Date)antitaskData[5]
                    ));
            break;
            default:
            System.out.println("Could not create task.");
        }

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
        // TODO: Hook up FileIO's writeFile() method.
        // fileIO.writeFile();
    }

    public void readFile() {
        // TODO: Hook up FileIO's readFile() method.
        // fileIO.readFile();
    }
}