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
import java.util.Arrays;

public class FileIO {

    String filename;

    public void readFile(List<Task> tasks, String filename) throws IOException {
      try {
            String test = getFileData(filename);
            //System.out.println(test); //prints what was read from file
            System.out.println("testing this is the updates version --");
            String[] allTasks = test.split("\\{");
            System.out.println(Arrays.toString(allTasks));
            
            List<String[]> taskData = new ArrayList<>();
            // starting at i = 1 gets rid of strange bracket at i = 0
            for(int i = 1; i<allTasks.length; i++){
            	String[] split = allTasks[i].split("[:,]");
            	// removes whitespace from all tasks
            	for (int j = 0; j < split.length; j++) {
            		split[j] = split[j].replaceAll("\\s", "");
            	}
            	// ensuring that last blank element gets removed from array, does not apply for last one
            	if (i != allTasks.length-1) {
            		String[] temp = new String[split.length-1];
            		for (int k = 0; k < split.length-1; k++) {
            			temp[k] = split[k];
            		}	
            		split = temp;
            		// the last one will have two characters at the end that must be removed, so this is done one extra time
            	} else {
            		split[split.length-1] = split[split.length-1].substring(0,split[split.length-1].length()-1);
            	}
            	split[split.length-1] = split[split.length-1].substring(0,split[split.length-1].length()-1);
            	System.out.println(Arrays.toString(split));
                taskData.add(split);
                
            }
            // printing out all taskData values
            for (int i = 0; i < taskData.size(); i++) {
            	for (int j = 0; j < taskData.get(i).length; j++) {
            		System.out.println(taskData.get(i)[j]);
            	}
            }

            //individual data slots;
             for( int i = 0; i < taskData.size(); i++ ) {

                  //index 3 holds the task type -- length 14 will be recurring task
                  if(taskData.get(i).length == 14){
                        tasks.add(createTaskfromFile(tasks, taskData, "recurringtask", i, taskData.get(i).length));
                  }
                  //checks if task type is cancellation -- will be antitask
                  else if(taskData.get(i)[3].equals("Cancellation")){
                	  tasks.add(createTaskfromFile(tasks, taskData, "antitask", i, taskData.get(i).length));
                  //everything else is transient by process of elimination
                  } else {
                	  tasks.add(createTaskfromFile(tasks, taskData, "transienttask", i, taskData.get(i).length));
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

    private Task createTaskfromFile(List<Task> tasks, List<String[]> taskData, String task, int index, int length){
      String stringMinusQuotes = "";
      String combinedValues = "";
      Object[] arr;

      for(int i = 1; i<length; i+=2){
        stringMinusQuotes = taskData.get(index)[i];
        stringMinusQuotes = stringMinusQuotes.substring(1,stringMinusQuotes.length()-1);

        //makes tasks comma delimited in one string so we can break it into an array in the switch statement
        if (i != length-1) {
        	combinedValues += stringMinusQuotes +",";
        } else {
        	combinedValues += stringMinusQuotes;
        }
        //System.out.println(combinedValues); //comma delimited values for each task

      }
      System.out.println(combinedValues);
      arr = combinedValues.split(",");


      switch(task) {
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
        
        //the array holds everything we need, printed here so its easier to see what's in it.
        //name type start date startTime endTime endDate frequency
        for(Object dataItem: arr)
          System.out.print(dataItem + " ");
        System.out.println();
        
        // shift array elements around to fit task creation
        // name type startTime endTime start date endDate frequency
        String[] recurringTypes = {"recurringtask", (String)arr[1]};
        return new RecurringTask((String)arr[0], recurringTypes, Time.intsToTime(Time.conversion((String)arr[3])), Time.intsToTime(Time.conversion((String)arr[4])), Date.intsToDate(Date.conversion((String)arr[2])), Date.intsToDate(Date.conversion((String)arr[5])), Integer.parseInt((String)arr[6]));

        case "transienttask":
        	String[] transientTypes = {"transienttask", (String)arr[1]};
        	time = 0;

            date = convertDate((String)arr[2]);
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
            
            // shift array elements around to fit task creation
            // name type startTime endTime startDate
           return new TransientTask((String)arr[0], transientTypes, Time.intsToTime(Time.conversion((String)arr[3])), Time.intsToTime(Time.conversion((String)arr[4])), Date.intsToDate(Date.conversion((String)arr[2])));
        case "antitask":
        	String[] antitaskTypes = {"antitask", (String)arr[1]};
        	time = 0;

            date = convertDate((String)arr[2]);
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
            
            // shift array elements around to fit task creation
            // name type startTime endTime startDate
           return new AntiTask((String)arr[0], antitaskTypes, Time.intsToTime(Time.conversion((String)arr[3])), Time.intsToTime(Time.conversion((String)arr[4])), Date.intsToDate(Date.conversion((String)arr[2])));

        default:
            break;
    }
	return null;
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