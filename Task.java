public abstract class Task {
    private String name;
    private String type;
    private double startTime;
    private double duration;
    private int date;
    
    public Task(String name, String type, double startTime, double duration, int date) {
        this.name = name;
        this.type = type;
        this.startTime = startTime;
        this.duration = duration;
        this.date = date;
    }
}