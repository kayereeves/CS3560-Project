public abstract class Task {
    private String name;
    private String type;
    private float startTime;
    private float duration;
    private int date;
    
    public Task(String name, String type, float startTime, float duration, int date) {
        this.name = name;
        this.type = type;
        this.startTime = startTime;
        this.duration = duration;
        this.date = date;
    }
}