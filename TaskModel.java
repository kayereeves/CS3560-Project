import java.util.List;

public class TaskModel {
    private List<Task> taskData;
    private FileIO fileIO;

    public List<Task> returnTasks() {
        return taskData;
    }

    // TODO: Accessor Methods

    public void createTask() {
        // TODO: Create task method
        // taskData.add(task);
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
