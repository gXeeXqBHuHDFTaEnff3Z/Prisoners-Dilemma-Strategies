package de.vertedge.prisonersdilemmastrategies.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import de.vertedge.prisonersdilemmastrategies.MainActivity;
import de.vertedge.prisonersdilemmastrategies.R;
import de.vertedge.prisonersdilemmastrategies.databinding.FragmentDashboardBinding;
import de.vertedge.prisonersdilemmastrategies.model.Tournament;

public class DashboardFragment extends Fragment implements MainActivity.Listener{

    private FragmentDashboardBinding binding;
    private RecyclerView recyclerViewResults;
    private ResultsRecyclerViewAdapter recyclerViewAdapter;
    private DashboardViewModel dashboardViewModel;
    private MainActivity context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = (MainActivity) getActivity();
        context.addListener( this );

        // create grid layout with the appropriate number of recycler columns
        final int columns = getResources().getInteger(R.integer.recyclerview_columns);
        GridLayoutManager _layout = new GridLayoutManager( getContext(), columns );
        recyclerViewResults = root.findViewById(R.id.rv);
        recyclerViewResults.setLayoutManager( _layout );

        // create adapter
        recyclerViewAdapter = new ResultsRecyclerViewAdapter();
        recyclerViewResults.setAdapter(recyclerViewAdapter);

        try {
            dashboardViewModel.getResults().observe(context, recyclerViewAdapter::updateList);
        } catch (java.lang.NullPointerException npe){
            // no results yet, do nothing
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResults(ArrayList<Tournament.ResultsPairing> results) {
        dashboardViewModel.setResults(results);
        dashboardViewModel.getResults().observe(context, recyclerViewAdapter::updateList);
    }
}