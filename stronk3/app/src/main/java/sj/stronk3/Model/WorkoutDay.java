package sj.stronk3.Model;

import java.util.List;

/**
 * Created by Corsair on 9-6-2016.
 * Workout represents a complete workout for one training.
 */
public class WorkoutDay {
    private int id;
    private int order;
    private List<Exercise> exercises;

    public WorkoutDay(int id, int order, List<Exercise> exercises) {
        this.id = id;
        this.order = order;
        this.exercises = exercises;
    }

    public int getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
}
