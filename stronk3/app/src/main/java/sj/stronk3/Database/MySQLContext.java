package sj.stronk3.Database;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
    public boolean insertWeight(double weight) {
        Utility.println("INSERTING WEIGHT: " + Double.toString(weight));
        String result = null;
        try {
            result = new DatabaseTask(activity, "INSERT INTO Weight(weight,date)VALUES(" + weight + ",convert_tz(now(),@@session.time_zone,'+02:00'))", QueryTypes.EXECUTE, true).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return (result == "1" ? true : false);
    }

    @Override
    public double getLastInsertedWeight() {
        double weight = 0;
        try {
            String result = new DatabaseTask(activity, Queries.GET_LAST_INSERTED_WEIGHT).execute().get();
            Utility.println("LAST INSERTED WEIGHT STRING: " + result);
            JSONArray jsonArray = parseJSON(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            weight = Double.parseDouble(jsonObject.getString("weight"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weight;
    }

    @Override
    public boolean deleteLastInsertedWeight() {
        String result = null;
        try {
            result = new DatabaseTask(activity, Queries.DELETE_LAST_INSERTED_WEIGHT).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return (result == "1" ? true : false);
    }

    @Override
    public boolean deleteWeight(int id) {
        String result = null;
        try {
            result = new DatabaseTask(activity, "DELETE FROM Weight WHERE id = " + id, QueryTypes.EXECUTE).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return (result == "1" ? true : false);
    }

    @Override
    public List<WorkoutDay> getAllWorkoutDays() {
        List<WorkoutDay> workoutDays = new ArrayList<>();
        try {
            String result = new DatabaseTask(activity, Queries.GET_ALL_WORKOUTDAYS).execute().get();
            JSONArray jsonArray = parseJSON(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = Integer.parseInt(jsonObject.getString("id"));
                List<Exercise> exercises = getAllExercisesForWorkoutDay(id);

                WorkoutDay workoutDay = new WorkoutDay(id, exercises);
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
            String result = new DatabaseTask(activity, "SELECT e.* FROM WorkoutDay_Exercise we, Exercise e WHERE we.Exercise_id = e.id AND WorkoutDay_id = " + workoutDayId + " ORDER BY `order`", QueryTypes.READ).execute().get();
            JSONArray jsonArray = parseJSON(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                int sets = jsonObject.getInt("sets");
                int repetitions = jsonObject.getInt("repetitions");
                String name = jsonObject.getString("name");

                Exercise exercise = new Exercise(id, sets, repetitions, name);
                exercises.add(exercise);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exercises;
    }

    @Override
    public int getWorkoutDayForAccount(int accountId) {
        int id = 0;
        try {
            String result = new DatabaseTask(activity, "SELECT WorkoutDay_id FROM Account WHERE id = " + accountId, QueryTypes.READ).execute().get();
            JSONArray jsonArray = parseJSON(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            id = jsonObject.getInt("WorkoutDay_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
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
