package sj.stronk3.Database.impl;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import sj.stronk3.Database.DatabaseContext;
import sj.stronk3.Database.DatabaseTask;
import sj.stronk3.Database.DatabaseTask2;
import sj.stronk3.Database.PHPTask;
import sj.stronk3.Database.Queries;
import sj.stronk3.Model.WeightDate;
import sj.stronk3.Model.WorkoutDay;

/**
 * Created by Corsair on 6-6-2016.
 */
public class MySQLContext implements DatabaseContext {

    @Override
    public void insertWeight(Context context, int weight) {
        new DatabaseTask(context, PHPTask.INSERT_WEIGHT).execute(Integer.toString(weight));
    }

    @Override
    public void testGetAllWeights() {
        new DatabaseTask2(Queries.GET_ALL_WEIGHTS).execute();
    }

    @Override
    public List<WeightDate> getAllWeights() {
        List<WeightDate> weightDates = new ArrayList<>();
        try {
            String result = new DatabaseTask2(Queries.GET_ALL_WEIGHTS).execute().get();
            JSONArray jsonArray = parseJSON(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = Integer.parseInt(jsonObject.getString("id"));
                double weight = Double.parseDouble(jsonObject.getString("weight"));
                String dateString = jsonObject.getString("date");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = format.parse(dateString);

                WeightDate weightDate = new WeightDate(weight, date);
                weightDates.add(weightDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weightDates;
    }

    @Override
    public List<WorkoutDay> GetAllWorkouts() {
        return null;
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
