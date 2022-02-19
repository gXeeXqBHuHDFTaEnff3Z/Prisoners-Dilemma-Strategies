package de.vertedge.prisonersdilemmastrategies.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import de.vertedge.prisonersdilemmastrategies.R;
import de.vertedge.prisonersdilemmastrategies.model.Strategy;
import de.vertedge.prisonersdilemmastrategies.model.StrategyNice;
import de.vertedge.prisonersdilemmastrategies.model.StrategyRandom;
import de.vertedge.prisonersdilemmastrategies.model.StrategyRevenge;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Strategy>> mStrategies;

    public HomeViewModel() {}

    public LiveData<List<Strategy>> getStrategiesMutableLiveData() {
        if (mStrategies == null) {
            clear();
        }
        return mStrategies;
    }

    public void clear(){
        mStrategies = new MutableLiveData<>();

        // populate initial list
        // load list
        List<Strategy> strategyList = new ArrayList<>();
        strategyList.add(new StrategyNice(R.string.strategy_nice, R.drawable.ic_nice, R.string.strategy_nice_explanation));
        strategyList.add(new StrategyRandom(R.string.strategy_random, R.drawable.ic_dice, R.string.strategy_random_explanation));
        strategyList.add(new StrategyRevenge(R.string.strategy_revenge, R.drawable.ic_angry, R.string.strategy_revenge_explanation));

        // add list to liveData
        mStrategies.setValue(strategyList);
    }
}