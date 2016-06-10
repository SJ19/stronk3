package sj.stronk3.Database;

import android.content.Context;

import java.util.List;

import sj.stronk3.Model.Exercise;
import sj.stronk3.Model.WeightDate;
import sj.stronk3.Model.WorkoutDay;

/**
 * Created by Corsair on 6-6-2016.
 */
public interface DatabaseContext {
    void insertWeight(Context context, int weight);
    List<WeightDate> getAllWeights();
    List<WorkoutDay> GetAllWorkouts();
    void testGetAllWeights();
}
