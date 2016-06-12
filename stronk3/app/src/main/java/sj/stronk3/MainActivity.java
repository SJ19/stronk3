package sj.stronk3;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sj.stronk3.Database.Repository;
import sj.stronk3.Model.Exercise;
import sj.stronk3.Model.WorkoutDay;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Repository repository = new Repository(this);
    private Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        loadExercisesList();
        loadFinishButton();
    }

    private void loadFinishButton() {
        Button button = (Button) findViewById(R.id.buttonFinished);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFinishedButtonEnabled(false);
                repository.nextWorkoutDay(repository.getAccount());
                loadExercisesList();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            //loadExercisesList();
        } else if (id == R.id.nav_gallery) {
        } else if (id == R.id.nav_slideshow) {
        } else if (id == R.id.nav_manage) {
        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_send) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private String exerciseString(Exercise exercise, int set) {
        return exercise.getName() + " sets: (" + (set == -1 ? "W" : set) + "/" + exercise.getSets() + ") - reps: " + exercise.getRepetitions();
    }

    /**
     * Loads the exercises from database, only call once.
     */
    private void loadExercisesList() {
        WorkoutDay workoutDay = repository.getAccount().getWorkoutDay();

        List<String> exerciseStrings = new ArrayList<>();
        final List<Integer> currentSet = new ArrayList<>();

        final List<Exercise> exercises = workoutDay.getExercises();
        for (Exercise exercise : exercises) {
            exerciseStrings.add(exerciseString(exercise, -1));
            currentSet.add(-1);
        }

        ListAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_rows, exerciseStrings);

        final ListView listView = (ListView) findViewById(R.id.listViewExercises);
        listView.setAdapter(adapter);
        listView.setSelection(adapter.getCount() - 1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final int set = currentSet.get(position);

                // Set next set.
                ((TextView) view).setText(exerciseString(exercises.get(position), set + 1));
                currentSet.set(position, set + 1);

                final Snackbar snackbar = Snackbar.make(view, setDoneString(set, chronometer.getText().toString()), Snackbar.LENGTH_INDEFINITE);
                snackbar.show();

                final View sbView = snackbar.getView();
                final TextView tv = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextSize(20);

                // Reset chronometer
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();

                // On every tick, update the snackbar text
                chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                    @Override
                    public void onChronometerTick(Chronometer chronometer) {
                        snackbar.setText(setDoneString(set, chronometer.getText().toString()));

                        // On 2 minutes mark, play notification sound.
                        if (chronometer.getText().equals("02:00")) {
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                            r.play();
                        }
                    }
                });
            }
        });
    }

    private String setDoneString(int set, String time) {
        return "Wait 2 minutes for the next set. " + time;
    }

    public void setFinishedButtonEnabled(boolean enabled) {
        final Button finishedButton = (Button)findViewById(R.id.buttonFinished);
        finishedButton.setEnabled(enabled);
    }
}
