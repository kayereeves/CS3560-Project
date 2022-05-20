public abstract class Task {
    private String name;
    private String[] type;
    private Time startTime;
    private Time endTime;
    private double duration;
    private Date date;

    public Task(String name, String[] type, Time startTime, Time endTime, Date date) {
        this.name = name;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = (endTime.getTimeDouble() <= startTime.getTimeDouble()) ?
                (24.00 - startTime.getTimeDouble()) + endTime.getTimeDouble() :
                endTime.getTimeDouble() - startTime.getTimeDouble();
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String[] getType() {
        return type;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public double getDuration() {
        return duration;
    }

    public Date getDate() {
        return date;
    }

    public void print() {
        //don't delete, should be overridden by children
    }

    public String toString() {
        return "\n        \"Name\" : \"" + this.name
                + "\",\n        \"Type\" : \"" + this.type[1]
                + "\",\n        \"Date\" : \"" + this.date.getDateInt()
                + "\",\n        \"StartTime\" : \"" + this.startTime.getTimeDouble()
                + "\",\n        \"Duration\" : \"" + this.duration + "\"";
    }

    //helper method to determine if tasks overlap
    public static Boolean doTasksOverlap(Task t1, Task t2) {
        if (t1.getStartTime().lessThan(t2.getEndTime()) && t2.getStartTime().lessThan(t1.getEndTime())) {
            //TODO: check if dates also overlap here
            return true;
        }
        return false;
    }
}