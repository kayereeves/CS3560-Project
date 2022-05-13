import java.util.InputMismatchException;
import java.util.Scanner;

public class Date {
    public static final String pattern = "[0-9]?[0-9]/[0-9]?[0-9]/[0-9][0-9][0-9][0-9]";
    private final int MIN_YEAR = 2000; //we can change these if we want
    private final int MAX_YEAR = 2100;
    private int month; //1-12
    private int day; //1-31
    private int year; //2000-2100
    private int dateInt;
    private String dateString;

    public Date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
        this.dateString = this.month + "/" + this.day + "/" + this.year;

        String s = Integer.toString(this.year) + String.format("%02d", this.month) + String.format("%02d", this.day);
        this.dateInt = Integer.parseInt(s);
    }

    public Date dateFromInt(int dateInt) {
        String s = Integer.toString(dateInt);
        int m = Integer.parseInt(s.substring(4, 5));
        int d = Integer.parseInt(s.substring(6, 7));
        int y = Integer.parseInt(s.substring(0, 3));

        return new Date(m, d, y);
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

    //for use in writing to file
    public String getDateString() {
        return this.dateString;
    }

    public Boolean validEndDate(Date startDate) {
        if (this.year < startDate.year) {
            return false;
        } else if (this.year == startDate.year && this.month < startDate.month) {
            return false;
        } else if (this.year == startDate.year && this.month == startDate.month && this.day <= startDate.day) {
            return false;
        }
        return true;
    }

    //returns true if date is in valid range
    public Boolean validate() {
        if (this.month >= 1 && this.month <= 12) {
            switch (this.month) {
                //months with 31 days
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    if (this.day < 1 || this.day > 31) {
                        return false;
                    }
                    break;

                //months with 30 days
                case 9:
                case 4:
                case 6:
                case 11:
                    if (this.day < 1 || this.day > 30) {
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
                    } else {
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

    public static int[] conversion(String s) {
        String[] splitString = s.split("/");
        int[] arr = {Integer.parseInt(splitString[0]), Integer.parseInt(splitString[1]),
                Integer.parseInt(splitString[2])};

        return arr;
    }

    //parse date from string in mm/dd/yyyy format
    public static Date parseDate(Scanner sc) {
        String s = "";

        while (true) {
            try {
                s = sc.next(Date.pattern);
                break;
            } catch (InputMismatchException e) {
                System.out.print("Input error. Check format and try again: ");
                sc.next();
            }
        }

        int[] monthDayYear = conversion(s);

        return intsToDate(monthDayYear);
    }

    public static Date intsToDate(int[] array) {
        return new Date(array[0], array[1], array[2]);
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
