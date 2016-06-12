package sj.stronk3.Database;

import java.util.List;

import sj.stronk3.Model.Account;
import sj.stronk3.Model.Exercise;
import sj.stronk3.Model.WorkoutDay;

/**
 * Created by Corsair on 6-6-2016.
 */
public interface DatabaseContext {
    List<WorkoutDay> getAllWorkoutDays();
    List<Exercise> getAllExercisesForWorkoutDay(int workoutDayId);
    Account getAccount(int accountId);

    boolean setWorkoutDay(Account account, WorkoutDay workoutDay);
}
