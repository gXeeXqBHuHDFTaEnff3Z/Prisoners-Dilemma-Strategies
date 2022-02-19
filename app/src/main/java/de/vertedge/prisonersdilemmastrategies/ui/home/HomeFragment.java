package de.vertedge.prisonersdilemmastrategies.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Observer;

import de.vertedge.prisonersdilemmastrategies.MainActivity;
import de.vertedge.prisonersdilemmastrategies.R;
import de.vertedge.prisonersdilemmastrategies.databinding.FragmentHomeBinding;
import de.vertedge.prisonersdilemmastrategies.model.Strategy;
import de.vertedge.prisonersdilemmastrategies.model.Tournament;
import de.vertedge.prisonersdilemmastrategies.ui.dashboard.DashboardViewModel;

public class HomeFragment extends Fragment implements LifecycleOwner, MainActivity.Listener {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerViewSelectStrategies;
    private SelectStrategiesRecyclerViewAdapter recyclerViewAdapter;
    private HomeViewModel homeViewModel;
    private DashboardViewModel dashboardViewModel;
    private MainActivity context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // get view models
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = (MainActivity) getActivity();
        context.addListener( this );

        // create grid layout with the appropriate number of recycler columns
        final int columns = getResources().getInteger(R.integer.recyclerview_columns);
        GridLayoutManager _layout = new GridLayoutManager( getContext(), columns );
        recyclerViewSelectStrategies = root.findViewById(R.id.rv);
        recyclerViewSelectStrategies.setLayoutManager( _layout );

        // create adapter
        recyclerViewAdapter = new SelectStrategiesRecyclerViewAdapter( context );
        recyclerViewSelectStrategies.setAdapter(recyclerViewAdapter);
        homeViewModel.getStrategiesMutableLiveData().observe(context, recyclerViewAdapter::updateList);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResults(ArrayList<Tournament.ResultsPairing> results) {
        homeViewModel.clear();
    }
}