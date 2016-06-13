package sj.stronk3.Model;

/**
 * Created by Corsair on 12-6-2016.
 */
public class Account {
    private int id;
    private String username;
    private WorkoutDay workoutDay;

    public Account(int id, String username, WorkoutDay workoutDay) {
        this.id = id;
        this.username = username;
        this.workoutDay = workoutDay;
    }

    public int getId() {
        return id;
    }

    public void setWorkoutDay(WorkoutDay workoutDay) {
        this.workoutDay = workoutDay;
    }

    public WorkoutDay getWorkoutDay() {
        return workoutDay;
    }

    public String getUsername() {
        return username;
    }
}
