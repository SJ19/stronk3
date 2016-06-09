package sj.stronk3.Database.impl;

import android.content.Context;

import java.util.List;

import sj.stronk3.Database.DatabaseContext;
import sj.stronk3.Database.DatabaseTask;
import sj.stronk3.Database.PHPTask;
import sj.stronk3.Model.Weight;
import sj.stronk3.Model.Workout;

/**
 * Created by Corsair on 6-6-2016.
 */
public class MySQLContext implements DatabaseContext {

    @Override
    public void insertWeight(Context context, int weight) {
        new DatabaseTask(context, PHPTask.INSERT_WEIGHT).execute(Integer.toString(weight));
    }

    @Override
    public List<Weight> GetAllWeights() {
        return null;
    }

    @Override
    public List<Workout> GetAllWorkouts() {
        return null;
    }
}
