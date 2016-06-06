package sj.stronk3.Database;

import android.content.Context;

/**
 * Created by Corsair on 6-6-2016.
 */
public class Repository {
    private DatabaseContext databaseContext = new MySQLContext();

    public void insertWeight(Context context, int weight) {
        databaseContext.insertWeight(context, weight);
    }
}
