import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;

public class FileIO {

    String filename;

    public void readFile(List<Task> tasks, String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                String[] arr = s.split(",");

                switch(arr[1]) {
                    case "antitask":
                        tasks.add(new AntiTask(arr[0], arr[1], Time.intsToTime(Time.conversion(arr[2])),
                        Time.intsToTime(Time.conversion(arr[3])), Date.intsToDate(Date.conversion(arr[4]))));
                        break;

                    case "recurringtask":
                        tasks.add(new RecurringTask(arr[0], arr[1], Time.intsToTime(Time.conversion(arr[2])),
                        Time.intsToTime(Time.conversion(arr[3])), Date.intsToDate(Date.conversion(arr[4])),
                        Date.intsToDate(Date.conversion(arr[5])), Integer.parseInt(arr[6])));
                        break;

                    case "transienttask":
                        tasks.add(new TransientTask(arr[0], arr[1], Time.intsToTime(Time.conversion(arr[2])),
                        Time.intsToTime(Time.conversion(arr[3])), Date.intsToDate(Date.conversion(arr[4]))));
                        break;

                    default:
                        break;
                }
            }
            System.out.println("File read. Check updated task schedule.");
            System.out.println();
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not read file. Check that file exists.");
            System.out.println();
        }
    }

    public void writeFile(List<Task> tasks, String filename) {
        try {
        FileWriter file = new FileWriter(filename);
        BufferedWriter output = new BufferedWriter(file);
        output.write("[");
        int i = 0;

        for (Task task : tasks) {
            try {
                output.newLine();
                output.write("\t{");
                output.write(task.toString());
                output.newLine();
                output.write("\t}");

                //prevent trailing comma
                if (i++ != tasks.size() - 1) {
                    output.write(",");
                }
            }
            catch (Exception e) {
                e.getStackTrace();
            }
        }

            output.write("\n]");
            output.close();
        }

        catch (Exception e) {
            e.getStackTrace();
        }

        System.out.println("File written. If you wish, you may use it to retrieve your tasks later.");
        System.out.println();
    }
}