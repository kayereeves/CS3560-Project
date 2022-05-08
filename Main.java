public class Main {
    public static void main(String[] args) {

        TaskModel model = new TaskModel();
        TaskView view = new TaskView();

        TaskController controller = new TaskController(model, view);

        controller.menuSelection();
    }
}