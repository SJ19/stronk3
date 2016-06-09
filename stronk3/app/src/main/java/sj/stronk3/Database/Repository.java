package sj.stronk3.Database;

import android.content.Context;

import sj.stronk3.Database.impl.MySQLContext;

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
}
