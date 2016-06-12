package sj.stronk3.Database;

import java.util.List;

import sj.stronk3.Model.Exercise;
import sj.stronk3.Model.WorkoutDay;

/**
 * Created by Corsair on 6-6-2016.
 */
public interface DatabaseContext {
    boolean insertWeight(double weight);

    double getLastInsertedWeight();

    boolean deleteLastInsertedWeight();

    boolean deleteWeight(int id);

    List<WorkoutDay> getAllWorkoutDays();
    List<Exercise> getAllExercisesForWorkoutDay(int workoutDayId);
    int getWorkoutDayForAccount(int accountId);
}
