import java.util.InputMismatchException;
import java.util.Scanner;

public class Time {
    private static String pattern = "[0-9]?[0-9]:[0-9][0-9]";
    private int hours; //0-23
    private int minutes; //0-59
    private double timeDouble;
    
    public Time(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
        this.round();
        this.calcDouble();
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public double getTimeDouble() {
        return timeDouble;
    }

    private void calcDouble() {
        this.timeDouble = (double) this.hours + ((double) this.minutes / 60);
    }

    private static int[] conversion(String s) {
        String[] splitString = s.split(":");
        int[] arr = {Integer.parseInt(splitString[0]), Integer.parseInt(splitString[1])};

        return arr;
    }

    //parse time from string in hh:mm format
    public static Time parseTime(Scanner sc) {
        String s = "";

        while (true) {
            try {
                s = sc.next(Time.pattern);
                break;
            }
            catch(InputMismatchException e) {
                System.out.print("Input error. Check format and try again: ");
                sc.next();
            }
        }

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

    //rounds minutes to nearest 15
    private void round() {
        int mod = this.minutes % 15;

        if (mod >= 8) {
            this.minutes = minutes + (15 - mod);
        }
        else {
            this.minutes = this.minutes - mod;
        }

        if (this.minutes == 60) {
            this.minutes = 0;

            if (this.hours == 23) {
                this.hours = 0;
            }
            else {
                this.hours += 1;
            }
        }
    }

    //returns true if calling object time is earlier than arg
    public Boolean lessThan(Time toCompare) {
        if (this.getTimeDouble() < toCompare.getTimeDouble())
        {
            return true;
        }
        return false;
    }
}
