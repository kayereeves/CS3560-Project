import java.util.Scanner;
import java.lang.String;

public class Date {
    public static final String pattern = "[0-9]?[0-9]/[0-9]?[0-9]/[0-9][0-9][0-9][0-9]";
    private final int MIN_YEAR = 2000; //we can change these if we want
    private final int MAX_YEAR = 2100;
    private int month; //1-12
    private int day; //1-31
    private int year; //2000-2100
    private int dateInt;

    public Date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;

        String s = Integer.toString(this.year) + String.format("%02d", this.month) + String.format("%02d", this.day);
        this.dateInt = Integer.parseInt(s);
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getDateInt() {
        return dateInt;
    }

    //returns true if date is in valid range
    public Boolean validate() {
        if (this.month >= 1 && this.month <= 12) {
            switch(this.month) {
                //months with 31 days
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    if (this.day <= 1 || this.day >= 31) {
                        return false;
                    }
                    break;

                //months with 30 days
                case 9:
                case 4:
                case 6:
                case 11:
                    if (this.day <= 1 || this.day >= 30) {
                        return false;
                    }
                    break;

                //february
                case 2:
                    if (((this.year % 4 == 0) && 
                    !(this.year % 100 == 0))
                    || (this.year % 400 == 0)) {
                        //leap year
                        if (this.day < 1 || this.day > 29) {
                            return false;
                        }
                    }
                    else {
                        //non leap year
                        if (this.day < 1 || this.day > 28) {
                            return false;
                        }
                    }
                    break;
            }   

            if (this.year < MIN_YEAR || this.year > MAX_YEAR) {
                return false;
            }

            return true;
        }

        return false;
    }

    private static int[] conversion(String s) {
        String[] splitString = s.split("/");
        int[] arr = {Integer.parseInt(splitString[0]), Integer.parseInt(splitString[1]),
        Integer.parseInt(splitString[2])};

        return arr;
    }

    //parse date from string in mm/dd/yyyy format
    public static Date parseDate(Scanner sc) {
        String s = sc.next(Date.pattern);
        int[] monthDayYear = conversion(s);
        
        return new Date(monthDayYear[0], monthDayYear[1], monthDayYear[2]);
    }

    //helper method that prints the Date in mm/dd/yyyy format
    public void print() {
        if (month < 10) {
            System.out.print("0");
        }
        System.out.print(month + "/");
        if (day < 10) {
            System.out.print("0");
        }
        System.out.print(day + "/");
        System.out.print(year);
    }
}
