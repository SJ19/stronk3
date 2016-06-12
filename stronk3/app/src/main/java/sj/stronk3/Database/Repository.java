package sj.stronk3.Database;

import android.app.Activity;

import java.util.List;

import sj.stronk3.Model.Account;
import sj.stronk3.Model.WorkoutDay;

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

        workoutDays = databaseContext.getAllWorkoutDays();
        account = databaseContext.getAccount(ACCOUNT_ID);
    }

    ///

    public void nextWorkoutDay(Account account) {
        WorkoutDay workoutDay = getNextWorkoutDay(account);
        databaseContext.setWorkoutDay(account, workoutDay);
        account.setWorkoutDay(workoutDay);
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

    private WorkoutDay getNextWorkoutDay(Account account) {
        int currentOrder = account.getWorkoutDay().getOrder();
        for (WorkoutDay workoutDay : workoutDays) {
            if (workoutDay.getOrder() == (currentOrder + 1)) {
                return workoutDay;
            }
        }
        return getFirstWorkoutDayNEW();
    }

    private WorkoutDay getFirstWorkoutDayNEW() {
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
