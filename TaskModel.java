import java.util.*;

public class TaskModel {
    private List<Task> taskData = new ArrayList<Task>();
    private FileIO fileIO;
    private TaskView view;

    public List<Task> returnTasks() {
        return taskData;
    }

    // TODO: Accessor Methods

    public void createTask(String type) {

        switch(type){
            case "transient":

            Object[] transientData = view.retrieveTaskData(type);
            taskData.add(
                        new TransientTask(
                         String.valueOf(transientData[0]),
                         String.valueOf(transientData[1]),
                         Float.parseFloat(transientData[2]), //i thought this would work -- unsure how to handle this
                         Float.parseFloat(transientData[3]), //might be easier to use double?
                         (int)transientData[4]
                         ));
            break;
            case "recurring":
            Object[] recurringData = view.retrieveTaskData(type);
            taskData.add(
                        new RecurringTask(
                            String.valueOf(recurringData[0]),
                            String.valueOf(recurringData[1]),
                            Float.parseFloat(recurringData[2]), //i thought this would work -- unsure how to handle this
                            Float.parseFloat(recurringData[3]),
                            (int)transientData[4],
                            (int)transientData[5],
                            (int)transientData[6]
                        ));
            break;
            case "antitask":
            // TODO: responsibilies when prompted to create antitask
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
