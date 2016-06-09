package sj.stronk3.Database.impl;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sj.stronk3.Database.DatabaseContext;
import sj.stronk3.Model.Weight;
import sj.stronk3.Model.Workout;

/**
 * Created by Corsair on 9-6-2016.
 */
public class DummyContext implements DatabaseContext {

    public static List<Weight> weights = new ArrayList<>();
    public static List<Workout> workouts = new ArrayList<>();

    @Override
    public void insertWeight(Context context, int weight) {

        // Declare a new date with today's date.
        Date today = new Date();
        Weight weightToInsert = new Weight(weight, today);

        // Add the new date to the dummy weights list.
        weights.add(weightToInsert);
    }

    @Override
    public List<Weight> GetAllWeights() {
        return weights;
    }

    @Override
    public List<Workout> GetAllWorkouts() {
        return workouts;
    }
}
