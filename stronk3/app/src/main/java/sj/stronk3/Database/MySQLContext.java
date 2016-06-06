package sj.stronk3.Database;

import android.content.Context;

/**
 * Created by Corsair on 6-6-2016.
 */
public class MySQLContext implements DatabaseContext {

    @Override
    public void insertWeight(Context context, int weight) {
        new DatabaseTask(context, PHPTask.INSERT_WEIGHT).execute(Integer.toString(weight));
    }
}
