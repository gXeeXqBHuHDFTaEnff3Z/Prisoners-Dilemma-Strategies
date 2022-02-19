package de.vertedge.prisonersdilemmastrategies.model;

import java.util.Comparator;

public abstract class Strategy {

    private final int _nameRID;
    private final int _iconRID;
    private final int _explanationRID;

    public Strategy(int nameRID, int iconRID, int explanationRID) {
        _nameRID = nameRID;
        _iconRID = iconRID;
        _explanationRID = explanationRID;
    }

    /** called when the strategy plays against the partner, giving only his id as as cue
     *
     * @param partner   id of the game partner strategy
     * @return  TRUE IFF the player wants to cooperate this round
     */
    public abstract boolean play(int partner);

    /** tells the strategy what move its partner made in the round
     *
     * @param partner   id of the partner played against
     * @param play      move of the partner
     */
    public abstract void partnerMoved(int partner, boolean play);

    public int get_nameRID() {
        return _nameRID;
    }

    public int get_iconRID() {
        return _iconRID;
    }

    public int get_explanationRID() {
        return _explanationRID;
    }
}
