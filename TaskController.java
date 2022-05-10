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
            char response = this.view.displayMenu();

            switch (response) {
                case '1':
                    //display schedule
                    view.displaySchedule(this.model.getTasks());
                    break;
                case '2':
                    //edit schedule
                    taskSelection();
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
        char response = this.view.displayTaskPrompt();

        if (response=='1')
            model.createTask("transienttask", this.view);
        else if(response=='2')
            model.createTask("recurringtask", this.view);
        else if(response=='3')
            model.createTask("antitask", this.view);
        else{
            System.out.println(response + " is not a valid option, please try again.");
            System.out.println();
            taskSelection();
        }

    }
}