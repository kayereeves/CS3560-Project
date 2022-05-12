public class AntiTask extends Task{  
    AntiTask(String name, String[] type, Time startTime, Time endTime, Date date){
        super(name, type, startTime, endTime, date);
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
        System.out.print("Date: ");
        super.getDate().print();
        System.out.println();
    }
}