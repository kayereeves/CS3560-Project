public abstract class Task {
    private String name;
    private String type;
    private Time startTime;
    private Time endTime;
    private int duration;
    private Date date;
    
    public Task(String name, String type, Time startTime, Time endTime, int duration, Date date) {
        this.name = name;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.date = date;
    }
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public Time getStartTime() {
		return startTime;
	}

    public Time getEndTime() {
        return endTime;
    }
	
	public int getDuration() {
		return duration;
	}
	
	public Date getDate() {
		return date;
	}

    public void print() {
        //don't delete, should be overridden by children
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