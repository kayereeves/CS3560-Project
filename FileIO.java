import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FileIO {
   String filename;

   public void readFile(List<Task> tasks, String filename) throws IOException, ParseException{
    //String jsonText = "";
    JSONParser parser = new JSONParser();
       try {
           File file = new File(filename);
           
           Object obj = parser.parse(new FileReader(file));

            JSONObject jsonObject =  (JSONObject) obj;

            String name = (String) jsonObject.get("Name");
            System.out.println(name);

           System.out.println("File read. Check updated task schedule.");
           System.out.println();
          // bufferedReader.close();
       } 
       catch(FileNotFoundException e) {
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
               output.write("    {");
               output.write(task.toString());
               output.newLine();
               output.write("    }");
               //prevent trailing comma
               if (i++ != tasks.size() - 1) {
                   output.write(",");
               }
           }
           catch (Exception e) {
               e.getStackTrace();
           }
       }
           output.newLine();
           output.write("]");
           output.close();
       }
       catch (Exception e) {

           e.getStackTrace();
       }

       System.out.println("File written. If you wish, you may use it to retrieve your tasks later.");
       System.out.println();
   }
}