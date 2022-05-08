public class RecurringTask extends Task {
    private int endDate;
    private int frequency;

    RecurringTask(String name, String type, double startTime, double duration, int date, int endDate, int frequency){
        super(name,type,startTime,duration,date);
        this.endDate = endDate;
        this.frequency = frequency;
    }
}
