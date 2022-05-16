import java.io.IOException;
import java.util.Scanner;

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


    public void menuSelection() throws IOException {
        while (true) {
            char response = this.view.displayMenu();

            switch (response) {
                case '1':
                    //display schedule
                    viewSelection();
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

    public void viewSelection() {
        char response = this.view.displayViewSelectionPrompt();

        if (response == '4') {
            this.view.displaySchedule(model.getTasks());
            return;
        }

        Scanner sc = this.getView().getScanner();

        System.out.println("Enter a start date.");
        Date startDate = Date.parseDate(sc);
        while (!startDate.validate()) {
            System.out.println("Invalid calendar date");
            startDate = Date.parseDate(sc);
        }

        if (response == '1') {
            this.view.displaySchedule(this.model.getTasksByFilter('1', startDate));
        } else if (response == '2') {
            this.view.displaySchedule(this.model.getTasksByFilter('2', startDate));
        } else if (response == '3') {
            this.view.displaySchedule(this.model.getTasksByFilter('3', startDate));
        } else {
            System.out.println(response + " is not a valid option, please try again.");
            System.out.println();
            viewSelection();
        }
    }

    public void taskSelection() {
        char response = this.view.displayTaskSelectionPrompt();

        if (response == '1')
            taskTypeSelection();
        else if (response == '2') {
            System.out.println();
            System.out.print("Enter task name to remove: ");
            String taskName = this.view.getScanner().next();
            this.model.removeTask(taskName);
        } else if (response == '3') {
            System.out.println();
            System.out.print("Enter task name to edit: ");
            String taskName = this.view.getScanner().next();
            this.model.editTask(taskName);
        } else {
            System.out.println(response + " is not a valid option, please try again.");
            System.out.println();
            taskSelection();
        }
    }

    public void taskTypeSelection() {
        char response = this.view.displayTaskTypePrompt();

        if (response == '1') {
            response = this.view.displayTaskSubtypePrompt(1);
            if (response == '1') {
                String[] taskTypes = {"transienttask", "Visit"};
                model.createTask(taskTypes, this.view);
            } else if (response == '2') {
                String[] taskTypes = {"transienttask", "Shopping"};
                model.createTask(taskTypes, this.view);
            } else if (response == '3') {
                String[] taskTypes = {"transienttask", "Appointment"};
                model.createTask(taskTypes, this.view);
            } else {
                System.out.println(response + " is not a valid option, please try again.");
                System.out.println();
                taskTypeSelection();
            }
        } else if (response == '2') {
            response = this.view.displayTaskSubtypePrompt(2);
            if (response == '1') {
                String[] taskTypes = {"recurringtask", "Class"};
                model.createTask(taskTypes, this.view);
            } else if (response == '2') {
                String[] taskTypes = {"recurringtask", "Study"};
                model.createTask(taskTypes, this.view);
            } else if (response == '3') {
                String[] taskTypes = {"recurringtask", "Sleep"};
                model.createTask(taskTypes, this.view);
            } else if (response == '4') {
                String[] taskTypes = {"recurringtask", "Exercise"};
                model.createTask(taskTypes, this.view);
            } else if (response == '5') {
                String[] taskTypes = {"recurringtask", "Work"};
                model.createTask(taskTypes, this.view);
            } else if (response == '6') {
                String[] taskTypes = {"recurringtask", "Meal"};
                model.createTask(taskTypes, this.view);
            } else {
                System.out.println(response + " is not a valid option, please try again.");
                System.out.println();
                taskTypeSelection();
            }
        } else if (response == '3') {
            String[] taskTypes = {"antitask", "Cancellation"};
            model.createTask(taskTypes, this.view);
        } else {
            System.out.println(response + " is not a valid option, please try again.");
            System.out.println();
            taskTypeSelection();
        }
    }
}
