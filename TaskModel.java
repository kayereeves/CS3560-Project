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

    public void createTask(String type) {

        switch(type){
            case "transient":

            Object[] transientData = view.retrieveTaskData(type);
            taskData.add(
                        new TransientTask(
                         String.valueOf(transientData[0]),
                         String.valueOf(transientData[1]),
                         Double.parseDouble((String) transientData[2]), //i thought this would work -- unsure how to handle this
                         Double.parseDouble((String) transientData[3]), //might be easier to use double?
                         (int)transientData[4]
                         ));
            break;
            case "recurring":
            Object[] recurringData = view.retrieveTaskData(type);
            taskData.add(
                        new RecurringTask(
                            String.valueOf(recurringData[0]),
                            String.valueOf(recurringData[1]),
                            Double.parseDouble((String) recurringData[2]), //i thought this would work -- unsure how to handle this
                            Double.parseDouble((String) recurringData[3]),
                            (int)recurringData[4],
                            (int)recurringData[5],
                            (int)recurringData[6]
                        ));
            break;
            case "antitask":
            Object[] antitaskData = view.retrieveTaskData(type);
            taskData.add(
                    new AntiTask(
                        String.valueOf(antitaskData[0]),
                        String.valueOf(antitaskData[1]),
                        Double.parseDouble((String) antitaskData[2]), //i thought this would work -- unsure how to handle this
                        Double.parseDouble((String) antitaskData[3]),
                        (int)antitaskData[4]
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