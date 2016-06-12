package sj.stronk3.Model;

/**
 * Created by Corsair on 12-6-2016.
 */
public class Account {
    private int id;
    private WorkoutDay workoutDay;

    public Account(int id, WorkoutDay workoutDay) {
        this.id = id;
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
}
