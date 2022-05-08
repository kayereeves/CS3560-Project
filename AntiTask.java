public class AntiTask extends Task{  
    AntiTask(String name, String type, Time startTime, Time endTime, double duration, Date date){
        super(name, type, startTime, endTime, duration, date);
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
        System.out.print("Date: ");
        super.getDate().print();
        System.out.println();
    }
}