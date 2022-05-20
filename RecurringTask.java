import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class RecurringTask extends Task {
    private Date date;
    private Date endDate;
    private int frequency;
    List<Integer> recurringDates = new ArrayList<Integer>();


    RecurringTask(String name, String[] type, Time startTime, Time endTime, Date date, Date endDate, int frequency) {
        super(name, type, startTime, endTime, date);
        this.date = date;
        this.endDate = endDate;
        this.frequency = frequency;
        this.calculateRecurringDates();
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getFrequency() {
        return frequency;
    }

    ////////////////////// Recurring Dates
    public void calculateRecurringDates() {
        for (int i = 0; i < (calculateDateDuration(date, endDate) / frequency) + frequency; i++) {
            LocalDate startDate = LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
            LocalDate newDate = startDate.plusDays((long) frequency * i);
            Date date = convertLocalDateToDate(newDate);
            String strDate = String.valueOf(date.getDateInt());
            if (calculateDateDuration(date, endDate) >= 0) {
                System.out.println("RecurringDate (" + i + "): " + strDate.substring(4, 6) + "/" + strDate.substring(6, 8) + "/" + strDate.substring(0, 4));
                recurringDates.add(date.getDateInt());
            } else {
                break;
            }
        }
    }

    public Date convertLocalDateToDate(LocalDate localDate) {
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        return new Date(month, day, year);
    }

    public int calculateDateDuration(Date start, Date end) {
        LocalDate startDate = LocalDate.of(start.getYear(), start.getMonth(), start.getDay());
        LocalDate endDate = LocalDate.of(end.getYear(), end.getMonth(), end.getDay());

        //System.out.print("DAYS BETWEEN: " + ((int) DAYS.between(startDate, endDate)) + " ");
        return (int) DAYS.between(startDate, endDate);
    }
    ////////////////////// End Recurring Dates

    public String toString() {
        return "\n        \"Name\" : \"" + super.getName()
                + "\",\n        \"Type\" : \"" + super.getType()[1]
                + "\",\n        \"StartDate\" : \"" + super.getDate().getDateInt()
                + "\",\n        \"StartTime\" : \"" + super.getStartTime().getTimeDouble()
                + "\",\n        \"Duration\" : \"" + super.getDuration()
                + "\",\n        \"EndDate\" : \"" + this.endDate.getDateInt()
                + "\",\n        \"Frequency\" : \"" + this.frequency + "\"";
    }


    //prints task info
    public void print() {
        System.out.println("Name: " + super.getName());
        System.out.println("Type: " + super.getType()[1]);
        System.out.print("Start Time: ");
        super.getStartTime().print();
        System.out.println();
        System.out.print("End Time: ");
        super.getEndTime().print();
        System.out.println();
        System.out.print("Duration: " + super.getDuration() + " hours");
        System.out.println();
        System.out.print("Start Date: ");
        super.getDate().print();
        System.out.println();
        System.out.print("End Date: ");
        this.endDate.print();
        System.out.println();
        System.out.println("Frequency: " + this.frequency);
    }
}