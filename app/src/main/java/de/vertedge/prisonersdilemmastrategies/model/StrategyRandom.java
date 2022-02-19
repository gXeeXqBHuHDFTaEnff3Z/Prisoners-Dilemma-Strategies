package de.vertedge.prisonersdilemmastrategies.model;

import java.util.Random;

public class StrategyRandom extends Strategy{

    private final Random random;

    public StrategyRandom(int nameRID, int iconRID, int explanationRID) {
        super(nameRID, iconRID, explanationRID);
        random = new Random();
    }

    @Override
    public boolean play(int partner) {
        return random.nextBoolean();
    }

    @Override
    public void partnerMoved(int partner, boolean play) {
        // do nothing
    }
}
