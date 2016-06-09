package sj.stronk3.Database;

import android.content.Context;

import java.util.List;

import sj.stronk3.Model.Exercise;
import sj.stronk3.Model.Weight;
import sj.stronk3.Model.Workout;

/**
 * Created by Corsair on 6-6-2016.
 */
public interface DatabaseContext {
    void insertWeight(Context context, int weight);
    List<Weight> GetAllWeights();
    List<Workout> GetAllWorkouts();
}
