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
        Utility.println("nextday:" + workoutDay.getName());
        account.setWorkoutDay(workoutDay);
        databaseContext.setWorkoutDay(account, workoutDay);
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
        Utility.println(account.getWorkoutDay().getName() + ", " +account.getWorkoutDay().getId());
        int currentDayIndex = -1;
        for(WorkoutDay workoutDay : workoutDays ) {
            if (workoutDay.getId() == account.getWorkoutDay().getId()) {
                 currentDayIndex = workoutDays.indexOf(workoutDay);
            }
        }
        int lastIndex = (workoutDays.size() - 1);
        Utility.println("current:"+currentDayIndex+",lastindex:"+lastIndex);
        if (currentDayIndex < lastIndex) {
            return workoutDays.get(currentDayIndex + 1);
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
