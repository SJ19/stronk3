package sj.stronk3.Database;

import android.content.Context;

import java.util.List;

import sj.stronk3.Database.impl.MySQLContext;
import sj.stronk3.Model.WeightDate;

/**
 * Created by Corsair on 6-6-2016.
 */
public class Repository {
    private DatabaseContext databaseContext;

    public Repository() {
        databaseContext = new MySQLContext();
    }

    public Repository(DatabaseContext databaseContext) {
        this.databaseContext = databaseContext;
    }

    public void insertWeight(Context context, int weight) {
        databaseContext.insertWeight(context, weight);
    }

    public void testGetAllWeights() {
        databaseContext.testGetAllWeights();
    }

    public List<WeightDate> getAllWeights() {
        return databaseContext.getAllWeights();
    }
}
