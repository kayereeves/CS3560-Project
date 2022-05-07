public class TaskController {
    private TaskModel model;
    private TaskView view;

    public TaskController(TaskModel model, TaskView view) {
        this.model = model;
        this.view = view;
    }

    // TODO: Accessor methods

    public void updateView() {
        view.displaySchedule();
    }
}
