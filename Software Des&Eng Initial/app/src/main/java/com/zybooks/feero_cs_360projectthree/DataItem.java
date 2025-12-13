package com.zybooks.feero_cs_360projectthree;

// Initialize all variables within a single DataItem (entry)
public class DataItem {
    private final long id;
    private final String username;
    public String weight;
    public String date;
    public String dailyIntake;
    public String dailyExercise;

    // Constructor method for all fields of a DataItem
    public DataItem(long id, String username, String weight, String date,
                    String dailyIntake, String dailyExercise) {
        this.id = id;
        this.username = username;
        this.weight = weight;
        this.date = date;
        this.dailyIntake = dailyIntake;
        this.dailyExercise = dailyExercise;
    }

    // Getter methods for all fields of a DataItem
    public long getId() { return id; }
    public String getUsername() { return username; }
    public String getDate() { return date; }
    public String getWeight() { return weight; }
    public String getDailyIntake() { return dailyIntake; }
    public String getExercise() { return dailyExercise; }
}
