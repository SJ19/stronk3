package sj.stronk3.Model;

import java.util.List;

/**
 * Created by Corsair on 9-6-2016.
 * Workout represents a complete workout for one training.
 */
public class WorkoutDay {
    private int id;
    private List<Exercise> exercises;

    public WorkoutDay(int id, List<Exercise> exercises) {
        this.id = id;
        this.exercises = exercises;
    }

    public int getId() {
        return id;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
}
