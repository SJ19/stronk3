package sj.stronk3;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONObject;

import java.util.List;

import sj.stronk3.Database.DatabaseTask;
import sj.stronk3.Database.PHPTask;
import sj.stronk3.Database.Repository;
import sj.stronk3.Model.WeightDate;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Repository repository = new Repository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
            // Handle the camera action
            repository.insertWeight(this, 5);
        } else if (id == R.id.nav_gallery) {
            List<WeightDate> weights = repository.getAllWeights();
            for(WeightDate weight : weights) {
                Utility.println("lool"+weight.getWeight());
            }
        } else if (id == R.id.nav_slideshow) {
            JSONParser jsonParser = new JSONParser();
            List<WeightDate> weightDates = jsonParser.getWeightDates("[{ \"id\": \"1\", \"weight\": \"1.5\", \"date\": \"2016-06-06 03:12:16\" }, { \"id\": \"2\", \"weight\": \"2\", \"date\": \"2016-06-06 02:16:18\" }, { \"id\": \"3\", \"weight\": \"3\", \"date\": \"2016-06-06 10:25:54\" }, { \"id\": \"4\", \"weight\": \"1\", \"date\": \"2016-06-06 11:31:48\" }, { \"id\": \"5\", \"weight\": \"1\", \"date\": \"2016-06-06 11:35:36\" }, { \"id\": \"6\", \"weight\": \"1\", \"date\": \"2016-06-06 11:36:23\" }, { \"id\": \"7\", \"weight\": \"1\", \"date\": \"2016-06-06 11:38:07\" }, { \"id\": \"8\", \"weight\": \"1\", \"date\": \"2016-06-06 11:39:03\" }, { \"id\": \"9\", \"weight\": \"5\", \"date\": \"2016-06-06 11:47:30\" }]");
            Log.d("app", "WEIGHT 1:" + weightDates.get(1).getWeight());
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
