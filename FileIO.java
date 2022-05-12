import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONObject;

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
      JSONObject object = toJSON(tasks);
     try{
      FileWriter file = new FileWriter(filename);
       file.write(object.toJSONString());
       file.flush();
       file.close();
     } catch (IOException e){
       e.printStackTrace();
     }
     
     
     /**  try {
        FileWriter file = new FileWriter(filename);
        BufferedWriter output = new BufferedWriter(file);
        JSONObject jsonData = new JSONObject();

        tasks.forEach((task) -> {
            try {
                output.write(task.toString());
                output.newLine();
            }
            catch (Exception e) {
                //e.getStackTrace();
            }
        }
        );

            output.close();
        }

        catch (Exception e) {
            e.getStackTrace();
        } */

        System.out.println("File written. If you wish, you may use it to retrieve your tasks later.");
        System.out.println();
    }

    public JSONObject toJSON(List<Task> tasks){

      JSONObject data = new JSONObject();
      data.put("Name", tasks.get(0));
      data.put("Type", tasks.get(1));
      data.put("Start Time", tasks.get(2));
      data.put("End Time", tasks.get(3));
      //data.put("Duration", tasks.get(4));
      data.put("Date", tasks.get(4));

      if(tasks.get(1).toString() == "recurringtask"){
        data.put("End Date", tasks.get(5));
        data.put("Frequency", tasks.get(6));
      }

      return data;

    }
}