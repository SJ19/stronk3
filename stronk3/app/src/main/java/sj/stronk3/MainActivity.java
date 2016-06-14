package sj.stronk3;

import android.app.Dialog;
import android.content.DialogInterface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
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
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sj.stronk3.Database.MySQLContext;
import sj.stronk3.Database.Repository;
import sj.stronk3.Model.Exercise;
import sj.stronk3.Model.WorkoutDay;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Repository repository = new Repository(this);
    private Chronometer chronometer;
    private Button finishedButton;
    private Button nextButton;

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
        loadNextWorkoutButton();
    }

    private void loadNextWorkoutButton() {
        nextButton = (Button) findViewById(R.id.buttonNextWorkout);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonsEnabled(false);
                repository.nextWorkoutDay();
                loadExercisesList();

                Toast toast = Toast.makeText(getApplicationContext(), "New workout loaded", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 400);
                toast.show();
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
        return exercise.getName() + " (" + (set == -1 ? "W" : set) + "/" + exercise.getSets() + ") - " + exercise.getRepetitions() + " * " + exercise.getWeight() + "KG";
    }

    /**
     * Loads the exercises from database, only call once.
     */
    private void loadExercisesList() {
        WorkoutDay workoutDay = repository.getAccount().getWorkoutDay();
        // getActionBar().setTitle("WorkoutDay: " + workoutDay.getName());//null
        getSupportActionBar().setTitle("WorkoutDay: " + workoutDay.getName());

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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                numberPickerDialog(exercises.get(position));
                return true;
            }
        });

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
                tv.setTextSize(25);
               // tv.setGravity(Gravity.CENTER_HORIZONTAL);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

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
        return time;
    }

    public void setButtonsEnabled(boolean enabled) {
        //finishedButton.setEnabled(enabled);
        nextButton.setEnabled(enabled);
    }

    private String[] nums = {
            "20", "22.5", "25", "27.5",
            "30", "32.5", "35", "37.5",
            "40", "42.5", "45", "47.5",
            "50", "52.5", "55", "57.5",
            "60", "62.5", "65", "67.5",
            "70", "72.5", "75", "77.5",
            "80", "82.5", "85", "87.5",
            "90", "92.5", "95", "97.5",
            "100", "102.5", "105", "107.5",
            "110", "112.5", "115", "117.5",
            "120", "122.5", "125", "127.5",
            "130", "132.5", "135", "137.5",
            "140", "142.5", "145", "147.5",
            "150", "152.5", "155", "157.5",
            "160", "162.5", "165", "167.5"
    };

    private void numberPickerDialog(final Exercise exercise) {
        final NumberPicker numberPicker = new NumberPicker(MainActivity.this);
        numberPicker.setDisplayedValues(nums);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(nums.length - 1);
        numberPicker.setWrapSelectorWheel(false);

        for (int i = 0; i < nums.length; i++) {
            if (Double.parseDouble(nums[i]) == exercise.getWeight()) {
                numberPicker.setValue(i);
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(numberPicker);
        builder.setTitle("Set weight for " + exercise.getName());
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                exercise.setWeight(Double.parseDouble(nums[numberPicker.getValue()]));
                repository.updateExerciseWeight(exercise);
                loadExercisesList();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}
