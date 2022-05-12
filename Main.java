import java.io.IOException;
//import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        TaskModel model = new TaskModel();
        TaskView view = new TaskView();

        TaskController controller = new TaskController(model, view);
        controller.menuSelection();
    }
}