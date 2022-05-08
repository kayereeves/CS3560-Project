public class RecurringTask extends Task {
    private Date endDate;
    private int frequency;

    RecurringTask(String name, String type, Time startTime, Time endTime, int duration, Date date, Date endDate, int frequency){
        super(name,type,startTime,endTime,duration,date);
        this.endDate = endDate;
        this.frequency = frequency;
    }
    
    public Date getEndDate() {
		return endDate;
	}
    
    public int getFrequency() {
    	return frequency;
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
        System.out.print("Start Date: ");
        super.getDate().print();
        System.out.println();
        System.out.print("End Date: ");
        endDate.print();
        System.out.println();
    }
}