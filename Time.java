import java.util.Scanner;

public class Time {
    private static String pattern = "[0-9]?[0-9]:[0-9][0-9]";
    private int hours; //0-23
    private int minutes; //0-59
    
    public Time(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    private static int[] conversion(String s) {
        String[] splitString = s.split(":");
        int[] arr = {Integer.parseInt(splitString[0]), Integer.parseInt(splitString[1])};

        return arr;
    }

    //parse time from string in hh:mm format
    public static Time parseTime(Scanner sc) {
        String s = sc.next(Time.pattern);
        int[] hoursAndMinutes = conversion(s);
        
        return new Time(hoursAndMinutes[0], hoursAndMinutes[1]);
    }

    //helper method that prints a Time in xx:xx format
    public void print() {
        if (hours < 10) {
            System.out.print("0");
        }
        System.out.print(hours + ":");
        if (minutes < 10) {
            System.out.print("0");
        }
        System.out.print(minutes);
    }

    //returns true if time is in valid range
    public Boolean validate() {
        if (this.hours >= 0 && this.hours <= 23) {
            if (this.minutes >= 0 && this.minutes <= 59) {
                return true;
            }
        }
        return false;
    }

    //returns true if calling object time is earlier than arg
    public Boolean lessThan(Time toCompare) {
        if (this.hours < toCompare.hours || (this.hours == toCompare.hours && this.minutes < toCompare.minutes))
        {
            return true;
        }
        return false;
    }
}
