package sj.stronk3.Database;

import java.util.List;

import sj.stronk3.Model.Account;
import sj.stronk3.Model.Exercise;
import sj.stronk3.Model.WorkoutDay;

/**
 * Created by Corsair on 6-6-2016.
 */
public interface DatabaseContext {
    List<WorkoutDay> getAllWorkoutDaysForAccount(Account account);
    List<Exercise> getAllExercisesForWorkoutDay(int accountId, int workoutDayId);
    Account getAccount(int accountId);
    boolean updateExerciseWeight(Account account, Exercise exercise);
    double getExerciseWeight(Account account, Exercise exercise);
    boolean setWorkoutDay(Account account, WorkoutDay workoutDay);
}
