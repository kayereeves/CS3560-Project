public class RecurringTask extends Task {
    private Date endDate;
    private int frequency;

    RecurringTask(String name, String type, Time startTime, Time endTime, Date date, Date endDate, int frequency){
        super(name, type, startTime, endTime, date);
        this.endDate = endDate;
        this.frequency = frequency;
    }
    
    public Date getEndDate() {
		return endDate;
	}
    
    public int getFrequency() {
    	return frequency;
    }

    public String toString() {
        return "\n\t\t\"name\":\"" + super.getName() + "\",\n\t\t\"type\":\"" + super.getType() + "\",\n\t\t\"start_time\":\""
        + super.getStartTime().getTimeString() + "\",\n\t\t\"end_time\":\"" + super.getEndTime().getTimeString() + "\",\n\t\t\"date\":\"" 
        + super.getDate().getDateString() + "\",\n\t\t\"end_date\":\"" + this.endDate.getDateString() + "\",\n\t\t\"frequency\":\"" 
        + this.frequency + "\"";
    }

    //prints task info
    public void print() {
        System.out.println("Name: " + super.getName());
        System.out.println("Type: " + super.getType());
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