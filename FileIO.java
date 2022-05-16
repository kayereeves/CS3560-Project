import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileIO {

    String filename;

    public void readFile(List<Task> tasks, String filename) throws IOException {
      try {
            String test = getFileData(filename);
            //System.out.println(test); //prints what was read from file
            System.out.println("testing this is the updates version --");
            String[] allTasks = test.split("\\{");

            
            List<String[]> taskData = new ArrayList<>();
            for(int i = 0; i<allTasks.length; i++){
                taskData.add(allTasks[i].split("[:,]"));
            }
            //System.out.println(taskData.get(1)[3]); //task type

            //individual data slots;
             for( int i = 1; i < taskData.size(); i++ ) {

                  //index 3 holds the task type -- length 14 will be recurring task
                  if(taskData.get(i).length == 14){
                        createTaskfromFile(tasks, taskData, "recurringtask", i, taskData.get(i).length);
                  }
                  //length 11 is transient task
                  else if(taskData.get(i).length == 11){
                    createTaskfromFile(tasks, taskData, "transienttask", i, taskData.get(i).length);
                  }
          //    }

          }

            System.out.println("File read. Check updated task schedule.");
            System.out.println();
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

    private String getFileData(String filename) throws IOException{
      String json = "";
        try {
            File file = new File(filename);
           
              BufferedReader reader = new BufferedReader(new FileReader(file));
            try {
                 StringBuilder sb = new StringBuilder();
                 String line = reader.readLine();

                 while (line != null) {
                     sb.append(line);
                     sb.append("\n");
                     line = reader.readLine();
                 }
                 json = sb.toString();
                 //System.out.println(json);

               } catch (IOException e) {
                e.printStackTrace();
              } finally {
                     reader.close();
                  }
            System.out.println("File read. Check updated task schedule.");
            System.out.println();
        } catch (FileNotFoundException e) {
            System.out.println("Could not read file. Check that file exists.");
            System.out.println();
        }
        return json;
    }

    private void createTaskfromFile(List<Task> tasks, List<String[]> taskData, String task, int index, int length){
      String stringMinusQuotes = "";
      String combinedValues = "";
      Object[] arr;

      for(int i = 1; i<length; i+=2){
        stringMinusQuotes = taskData.get(index)[i];
        stringMinusQuotes = stringMinusQuotes.substring(2,stringMinusQuotes.length()-1);

        //makes tasks comma delimited in one string so we can break it into an array in the switch statement
        combinedValues += stringMinusQuotes +",";
        //System.out.println(combinedValues); //comma delimited values for each task

      }
      arr = combinedValues.split(",");


      switch(task) {
        //case "antitask":
        //    tasks.add(new AntiTask((String)arr[0], (String[])arr[1], Time.intsToTime(Time.conversion((String)arr[2])),
        //    Time.intsToTime(Time.conversion((String)arr[3])), Date.intsToDate(Date.conversion((String)arr[4]))));
        //    break;

        case "recurringtask":
        double time = 0;
        String date = "";

        time = calculateEndTime(Double.valueOf((String)arr[3]),Double.valueOf((String)arr[4]));
        //make arr[4] hold endtime instead of duration:
        arr[4] = correctTimeFormat(String.valueOf(time));
 
        //now that end time is calculated we can correct start time format:
        arr[3] = correctTimeFormat(String.valueOf(arr[3]));


        //mm/dd/yyyy
        date = convertDate(((String)arr[2]));
        arr[2] = date;
        date = convertDate(((String)arr[5]));
        arr[5] = date;

        //removes extra quotation still in frequency
        arr[6] = ((String)arr[6]).substring(0, ((String)arr[6]).indexOf("\""));

        //the array holds everything we need, printed here so its easier to see what's in it.
        //name type start date startTime endTime endDate frequency
        for(Object dataItem: arr)
          System.out.print(dataItem + " ");
        System.out.println();
        
            //tasks.add(new RecurringTask((String)arr[0], (String[])arr[1], Time.intsToTime(Time.conversion((String)arr[2])),
            //Time.intsToTime(Time.conversion((String)arr[3])), Date.intsToDate(Date.conversion((String)arr[4])),
            //Date.intsToDate(Date.conversion((String)arr[5])), Integer.parseInt((String)arr[6])));
            break;

        case "transienttask":
          time = 0;

          //removes extra quotation still in duration
          arr[4] = ((String)arr[4]).substring(0, ((String)arr[4]).indexOf("\""));

          date = convertDate(((String)arr[2]));
          arr[2] = date;

          time = calculateEndTime(Double.valueOf((String)arr[3]),Double.valueOf((String)arr[4]));
          //make arr[4] hold endtime instead of duration:
          arr[4] = correctTimeFormat(String.valueOf(time));
          System.out.println("End time: " + arr[4]);
          //now that end time is calculated we can correct start time format:
          arr[3] = correctTimeFormat(String.valueOf(arr[3]));
          System.out.println("Start time: " + arr[3]);

          //the array holds everything we need, printed here so its easier to see what's in it.
          //name type startDate startTime endTime
          for(Object dataItem: arr)
          System.out.print(dataItem + " ");
          System.out.println();
        
           // tasks.add(new TransientTask((String)arr[0], (String[])arr[1], Time.intsToTime(Time.conversion((String)arr[2])),
            //Time.intsToTime(Time.conversion((String)arr[3])), Date.intsToDate(Date.conversion((String)arr[4]))));
            break;

        default:
            break;
    }
    }

    private double calculateEndTime(double startTime, double duration){
      return startTime+duration;
    }

    private String correctTimeFormat(String time){
      double minutes = 0;
      String minutesAsString = "";
      String hhmm = time.substring(0, time.indexOf(".")) + ":";

      //calculate minutes from decimal as found in json file
      minutes = Double.parseDouble("0" + time.substring(time.indexOf("."), time.length())) *60;
      minutesAsString = String.valueOf(minutes);
      //remove decimal
      minutesAsString = minutesAsString.substring(0, minutesAsString.indexOf("."));
      //hh:mm
      hhmm += minutesAsString;

      return hhmm;
    }

    private String convertDate(String date){
      String correctDate = "";
        correctDate += date.substring(4,6) +"/";
        correctDate += date.substring(6) +"/";
        correctDate += date.substring(0,4);

        return correctDate;
    }
}