package sj.stronk3.Database;

import android.app.Activity;

import java.util.List;

import sj.stronk3.Model.Exercise;
import sj.stronk3.Model.WorkoutDay;

/**
 * Created by Corsair on 6-6-2016.
 */
public class Repository {
    private DatabaseContext databaseContext;
    private Activity activity;

    public Repository(Activity activity) {
        databaseContext = new MySQLContext(activity);
        this.activity = activity;
    }

    public Repository(Activity activity, DatabaseContext databaseContext) {
        this.databaseContext = databaseContext;
        this.activity = activity;
    }

    public boolean insertWeight(double weight) {
        return databaseContext.insertWeight(weight);
    }

    public double getLastInsertedWeight() {
        return databaseContext.getLastInsertedWeight();
    }

    public boolean deleteLastInsertedWeight() {
        return databaseContext.deleteLastInsertedWeight();
    }

    public boolean deleteWeight(int id) {
        return databaseContext.deleteWeight(id);
    }

    public List<WorkoutDay> getAllWorkoutDays(){
        return databaseContext.getAllWorkoutDays();
    }

    public List<Exercise> getAllExercisesForWorkoutDay(int workoutDayId){
        return databaseContext.getAllExercisesForWorkoutDay(workoutDayId);
    }

    public int getWorkoutDayForAccount(int accountId) {
        return databaseContext.getWorkoutDayForAccount(accountId);
    }
}
