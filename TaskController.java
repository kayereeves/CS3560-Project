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

    public void taskSelection(){
        char response = view.displayTaskPrompt();

        if (response==1)
            model.createTask("transient");
        else if(response==2)
            model.createTask("recurring");
        else if(response==3)
            model.createTask("antitask");
        else{
            System.out.println(response + "is not a valid option, please try again.");
            taskSelection();
        }

    }
}
