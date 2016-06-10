package sj.stronk3;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sj.stronk3.Model.WeightDate;

/**
 * Created by Corsair on 10-6-2016.
 */
public class JSONParser {
    public List<WeightDate> getWeightDates(String jsonString) {
        List<WeightDate> weightDates = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = Integer.parseInt(jsonObject.getString("id"));
                double weight = Double.parseDouble(jsonObject.getString("weight"));
                String dateString = jsonObject.getString("date");
                Log.d("app","DATESTRING:"+dateString);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = format.parse(dateString);
                Log.d("app","DATE:"+date.toString());

                WeightDate weightDate = new WeightDate(weight, date);
                weightDates.add(weightDate);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return weightDates;
    }

}
