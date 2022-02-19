package de.vertedge.prisonersdilemmastrategies.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import de.vertedge.prisonersdilemmastrategies.model.Tournament;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<List<Tournament.ResultsPairing>> mResults;

    public DashboardViewModel() {
        this.mResults = null;
    }

    public void setResults(List<Tournament.ResultsPairing> results) {
        this.mResults = new MutableLiveData<>();
        mResults.setValue(results);
    }

    public LiveData<List<Tournament.ResultsPairing>> getResults() {
        return mResults;
    }
}