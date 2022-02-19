package de.vertedge.prisonersdilemmastrategies.model;

import android.util.Log;

import java.util.ArrayList;

public class StrategyRevenge extends Strategy{

    public StrategyRevenge(int nameRID, int iconRID, int explanationRID) {
        super(nameRID, iconRID, explanationRID);
    }

    private class Partner_remember_pair{
        int partnerID;
        boolean response;
    }

    ArrayList<Partner_remember_pair> pairs = new ArrayList<>();

    @Override
    public boolean play(int partner) {
        // find entry in archive
        Partner_remember_pair prp = null;
        for (Partner_remember_pair pp : pairs){
            if (pp.partnerID == partner){
                prp = pp;
            }
        }

        // if there is no entry, create new one and default to being nice
        if (prp == null){
            prp = new Partner_remember_pair();
            prp.partnerID = partner;
            prp.response = true;
            pairs.add(prp);
        }

        return prp.response;
    }

    @Override
    public void partnerMoved(int partner, boolean play) {
        // if he was nice, everything is fine
        if (play) return;

        // find partner and remember he must have defected
        for (Partner_remember_pair pp : pairs){
            if (pp.partnerID == partner){
                Log.d("REVENGE", partner + " lied: " + play);
                pp.response = false;
            }
        }
    }
}
