package sj.stronk3.Database;

import android.app.Activity;

import java.util.List;

import sj.stronk3.Model.Account;
import sj.stronk3.Model.Exercise;
import sj.stronk3.Model.WorkoutDay;
import sj.stronk3.Utility;

/**
 * Created by Corsair on 6-6-2016.
 */
public class Repository {
    private DatabaseContext databaseContext;
    private Activity activity;

    private List<WorkoutDay> workoutDays;
    private Account account;

    public static final int ACCOUNT_ID = 1;

    public Repository(Activity activity) {
        this(activity, new MySQLContext(activity));
    }

    public Repository(Activity activity, DatabaseContext databaseContext) {
        this.databaseContext = databaseContext;
        this.activity = activity;

        account = databaseContext.getAccount(ACCOUNT_ID);
        workoutDays = databaseContext.getAllWorkoutDaysForAccount(account);

        for (WorkoutDay workday : workoutDays) {
            Utility.println("yoo");
            Utility.println(workday.getName());
        }
    }

    ///

    public void nextWorkoutDay() {
        WorkoutDay workoutDay = getNextWorkoutDay();
        databaseContext.setWorkoutDay(account, workoutDay);
        account.setWorkoutDay(workoutDay);
    }

    public boolean updateExerciseWeight(Exercise exercise) {
        return databaseContext.updateExerciseWeight(account, exercise);
    }

    /// no databasecontext used below

    private WorkoutDay getWorkoutDay(int workoutDayId) {
        for (WorkoutDay workoutDay : workoutDays) {
            if (workoutDay.getId() == workoutDayId) {
                return workoutDay;
            }
        }
        return null;
    }

    private WorkoutDay getNextWorkoutDay() {
        int currentOrder = account.getWorkoutDay().getOrder();
        for (int i = 0; i < workoutDays.size(); i++) {
            if (workoutDays.get(i) == account.getWorkoutDay()) {
                int maxIndex = workoutDays.size() - 1;
                int nextIndex = i + 1;
                if (maxIndex < nextIndex) {
                    break;
                }
                WorkoutDay workoutDay = workoutDays.get(i + 1);
                return workoutDays.get(i + 1);
            }
        }
        return getFirstWorkoutDay();
    }

    private WorkoutDay getFirstWorkoutDay() {
        WorkoutDay firstWorkoutDay = workoutDays.get(0);
        for (WorkoutDay workoutDay : workoutDays) {
            if (workoutDay.getOrder() < firstWorkoutDay.getOrder()) {
                firstWorkoutDay = workoutDay;
            }
        }
        return firstWorkoutDay;
    }

    public Account getAccount() {
        return account;
    }

    public List<WorkoutDay> getWorkoutDays() {
        return workoutDays;
    }
}
