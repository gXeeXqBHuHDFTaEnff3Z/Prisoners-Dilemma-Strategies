package de.vertedge.prisonersdilemmastrategies;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

import de.vertedge.prisonersdilemmastrategies.databinding.ActivityMainBinding;
import de.vertedge.prisonersdilemmastrategies.model.Strategy;
import de.vertedge.prisonersdilemmastrategies.model.Tournament;
import de.vertedge.prisonersdilemmastrategies.ui.dashboard.DashboardFragment;
import de.vertedge.prisonersdilemmastrategies.ui.dashboard.DashboardViewModel;
import de.vertedge.prisonersdilemmastrategies.ui.home.SelectStrategiesRecyclerViewAdapter;

public class MainActivity extends AppCompatActivity implements Tournament.TournamentListener, SelectStrategiesRecyclerViewAdapter.StrategiesSelectListener {

    public interface Listener{
        public void onResults(ArrayList<Tournament.ResultsPairing> results);
    }

    private ActivityMainBinding binding;
    private ArrayList<Strategy> selectedStrategies;
    private ArrayList<Tournament.ResultsPairing> results;
    private NavController navController;
    private BottomNavigationView navView;
    ArrayList<Long> scores;
    private ArrayList<Listener> listeners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.select_menu, menu);

        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_start_tournament:
                assert selectedStrategies != null;
                Tournament tournament = new Tournament(selectedStrategies, 200, this);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void addListener(Listener listener){
        if (listeners == null){
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }

    @Override
    public void onSelect(ArrayList<Strategy> strategyArrayList) {
        assert strategyArrayList != null;
        this.selectedStrategies = strategyArrayList;
    }

    @Override
    public void OnTournamentRoundDone(long round) {
        // do nothing
        Log.d("round", round + "");
    }

    @Override
    public void OnTournamentDone(Tournament tournament) {
        // navigate to dashboard
        // navController.navigate( R.id.navigation_dashboard );

        // tell listeners
        if (listeners != null)
        for (Listener l : listeners){
            l.onResults(tournament.get_pairs());
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


}