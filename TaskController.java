public class TaskController {
    private TaskModel model;
    private TaskView view;

    public TaskController(TaskModel model, TaskView view) {
        this.model = model;
        this.view = view;
    }

    public TaskModel getModel() {
    	return model;
    }
    
    public TaskView getView() {
    	return view;
    }


    public void menuSelection() {
        while (true) {
            char response = view.displayMenu();

            switch (response) {
                case '1':
                    //display schedule
                    view.displaySchedule(model.getTasks());
                    break;
                case '2':
                    //edit schedule
                    model.editTask();
                    break;
                case '3':
                    //write schedule to file
                    model.writeFile();
                    break;
                case '4':
                    //read schedule from file
                    model.readFile();
                    break;
                case 'Q':
                case 'q':
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    view.displayInvalidResponse();
            }
        }
    }

    public void taskSelection(){
        char response = view.displayTaskPrompt();

        if (response==1)
            model.createTask("transient", view);
        else if(response==2)
            model.createTask("recurring", view);
        else if(response==3)
            model.createTask("antitask", view);
        else{
            System.out.println(response + "is not a valid option, please try again.");
            taskSelection();
        }

    }
}