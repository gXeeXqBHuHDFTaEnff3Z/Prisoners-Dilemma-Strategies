package de.vertedge.prisonersdilemmastrategies.model;

public class StrategyNice extends Strategy{
    public StrategyNice(int nameRID, int iconRID, int explanationRID) {
        super(nameRID, iconRID, explanationRID);
    }

    @Override
    public boolean play(int partner) {
        return true;
    }

    @Override
    public void partnerMoved(int partner, boolean play) {
        // do nothing
    }
}
