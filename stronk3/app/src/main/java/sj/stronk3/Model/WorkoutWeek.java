package sj.stronk3.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Corsair on 10-6-2016.
 */
public class WorkoutWeek {
    private int id;
    private List<WorkoutDay> workoutDays = new ArrayList<>();

    public WorkoutWeek(int id, List<WorkoutDay> workoutDays) {
        this.id = id;
        this.workoutDays = workoutDays;
    }
}
