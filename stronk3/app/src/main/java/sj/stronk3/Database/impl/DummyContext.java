package sj.stronk3.Database.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sj.stronk3.Database.DatabaseContext;
import sj.stronk3.Model.WeightDate;
import sj.stronk3.Model.WorkoutDay;

/**
 * Created by Corsair on 9-6-2016.
 */
public class DummyContext implements DatabaseContext {

    public static List<WeightDate> weights = new ArrayList<>();
    public static List<WorkoutDay> workouts = new ArrayList<>();

    @Override
    public void insertWeight(Context context, int weight) {

        // Declare a new date with today's date.
        Date today = new Date();
        WeightDate weightToInsert = new WeightDate(weight, today);

        // Add the new date to the dummy weights list.
        weights.add(weightToInsert);
    }

    @Override
    public List<WeightDate> getAllWeights() {
        return weights;
    }

    @Override
    public List<WorkoutDay> GetAllWorkouts() {
        return workouts;
    }

    @Override
    public void testGetAllWeights() {

    }
}
