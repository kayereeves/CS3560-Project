public class TaskController {
    private TaskModel model;
    private TaskView view;

    public TaskController(TaskModel model, TaskView view) {
        this.model = model;
        this.view = view;
    }

    // TODO: Accessor methods


    public void menuSelection() {
        char response = view.displayMenu();

        switch (response) {
            case '1':
                //display schedule
                view.displaySchedule(model.returnTasks());
                break;
            case '2':
                //edit schedule
                view.displayEditSchedule();
                model.editTask();
                break;
            case '3':
                //write schedule to file
                view.displayWriteFile();
                model.writeFile();
                break;
            case '4':
                //read schedule from file
                view.displayReadFile();
                model.readFile();
                break;
            default:
                view.displayInvalidResponse();
        }
    }
}
