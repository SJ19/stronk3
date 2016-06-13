package sj.stronk3.Database;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import sj.stronk3.Model.Account;
import sj.stronk3.Model.Exercise;
import sj.stronk3.Model.WorkoutDay;
import sj.stronk3.Utility;

/**
 * Created by Corsair on 6-6-2016.
 */
public class MySQLContext implements DatabaseContext {
    private Activity activity;

    public MySQLContext(Activity activity) {
        this.activity = activity;
    }

    @Override
    public List<WorkoutDay> getAllWorkoutDaysForAccount(Account account) {
        List<WorkoutDay> workoutDays = new ArrayList<>();
        try {
            String result = new DatabaseTask(activity, "SELECT * FROM WorkoutDay WHERE Account_id = " + account.getId() + " ORDER BY `order`", QueryTypes.READ).execute().get();
            JSONArray jsonArray = parseJSON(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String name = jsonObject.getString("name");
                int order = jsonObject.getInt("order");
                List<Exercise> exercises = getAllExercisesForWorkoutDay(id);

                WorkoutDay workoutDay = new WorkoutDay(id, name, order, exercises);
                workoutDays.add(workoutDay);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workoutDays;
    }

    @Override
    public List<Exercise> getAllExercisesForWorkoutDay(int workoutDayId) {
        List<Exercise> exercises = new ArrayList<>();
        try {
            String result = new DatabaseTask(activity, "SELECT e.*, ae.weight FROM WorkoutDay_Exercise we, Exercise e, Account_Exercise ae WHERE we.Exercise_id = e.id AND e.id = ae.Exercise_id AND WorkoutDay_id = "+workoutDayId+" ORDER BY `order`", QueryTypes.READ).execute().get();
            JSONArray jsonArray = parseJSON(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                int sets = jsonObject.getInt("sets");
                int repetitions = jsonObject.getInt("repetitions");
                String name = jsonObject.getString("name");
                double weight = jsonObject.getDouble("weight");
                Exercise exercise = new Exercise(id, sets, repetitions, weight, name);
                exercises.add(exercise);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exercises;
    }

    @Override
    public Account getAccount(int accountId) {
        WorkoutDay workoutDay = null;
        String username = null;
        try {
            String result = new DatabaseTask(activity, "SELECT * FROM Account WHERE id = " + accountId, QueryTypes.READ).execute().get();
            JSONArray jsonArray = parseJSON(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            username = jsonObject.getString("username");
            int workoutDayId = jsonObject.getInt("WorkoutDay_id");
            workoutDay = getWorkoutDay(workoutDayId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (workoutDay == null) {
            return null;
        }
        return new Account(accountId, username, workoutDay);
    }

    @Override
    public boolean updateExerciseWeight(Account account, Exercise exercise) {
        String result = null;
        try {
            result = new DatabaseTask(activity, "UPDATE Account_Exercise SET weight = " + exercise.getWeight() + " WHERE Account_id = "+account.getId()+" AND Exercise_id = " + exercise.getId(), QueryTypes.EXECUTE).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (result == "1" ? true : false);
    }

    @Override
    public double getExerciseWeight(Account account, Exercise exercise) {
        double weight = 0;
        try {
            String result = new DatabaseTask(activity, "SELECT weight FROM Account_Exercise WHERE Account_id = " + account.getId(), QueryTypes.READ).execute().get();
            JSONArray jsonArray = parseJSON(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            weight = jsonObject.getDouble("weight");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weight;
    }

    private WorkoutDay getWorkoutDay(int workoutDayId) {
        WorkoutDay workoutDay = null;
        try {
            String resultSelect = new DatabaseTask(activity, "SELECT * FROM WorkoutDay WHERE id = " + workoutDayId, QueryTypes.READ).execute().get();
            JSONArray jsonArray = parseJSON(resultSelect);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            int order = jsonObject.getInt("order");
            List<Exercise> exercises = getAllExercisesForWorkoutDay(id);
            workoutDay = new WorkoutDay(id, name, order, exercises);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workoutDay;
    }

    @Override
    public boolean setWorkoutDay(Account account, WorkoutDay workoutDay) {
        String result = null;
        try {
            result = new DatabaseTask(activity, "UPDATE Account SET WorkoutDay_id = " + workoutDay.getId() + " WHERE id = " + account.getId(), QueryTypes.EXECUTE).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (result == "1" ? true : false);
    }

    private JSONArray parseJSON(String jsonString) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}
