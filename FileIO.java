import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class FileIO {
    
    String filename;

    void readFile(String filename){
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
              //need to figure out how we want to read it
            }
            scanner.close();
          } catch (FileNotFoundException e) {
            System.out.println("Could not read file.");
          }
    }

    void writeFile(String filename){
        createFile(filename);
        try {
            FileWriter writer = new FileWriter(filename);
            // TODO: writing style
            writer.write("idk");
            writer.close();
          } catch (IOException e) {
            System.out.println("Could not write to file.");
            e.printStackTrace();
          }
    }

    private void createFile(String filename){
        try {
            File file = new File(filename);
            if (file.createNewFile()) {
              System.out.println("File created: " + file.getName());
            } else {
              System.out.println("File already exists.");
            }
          } catch (IOException e) {
            System.out.println("Could not create file");
          }
    }

    private boolean checkSyntax(){
        return true;
    }
}
