package de.vertedge.prisonersdilemmastrategies.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Tournament {

    public static class ResultsPairing{
        public Strategy strategy;
        public long score;

        public long getScore(){return score; }
    }

    public interface TournamentListener {
        public void OnTournamentRoundDone(long round);
        public void OnTournamentDone(Tournament tournament);
    }

    // GAME VARIABLES
    private final ArrayList<ResultsPairing> _pairs;
    private final long maxRounds;

    // OTHER VARIABLES
    TournamentListener listener;

    public Tournament(ArrayList<Strategy> _strategies, long maxRounds, TournamentListener listener) {
        this._pairs = new ArrayList<>();
        for (Strategy s : _strategies){
            ResultsPairing sp = new ResultsPairing();
            sp.strategy = s;
            sp.score = 0L;
            _pairs.add(sp);
        }
        this.listener = listener;
        this.maxRounds = maxRounds;
        play();
    }

    public long getMaxRounds() {
        return maxRounds;
    }

    public void play() {

        for (long i = 0; i < maxRounds; i++) {
            // tell the listener we are working on this round
            if (listener != null) {
                listener.OnTournamentRoundDone(i);
            }

            // go through all players and let them play against each other
            for (int j = 0, strategiesSize = _pairs.size(); j < strategiesSize; j++) {
                Strategy s1 = _pairs.get(j).strategy;
                for (int k = 0, size = _pairs.size(); k < size; k++) {
                    Strategy s2 = _pairs.get(k).strategy;

                    // find out what the partner will play
                    boolean s1play = s1.play(k);
                    boolean s2play = s2.play(j);

                    // award the scores accordingly
                    outcome(j, s1play, k, s2play);

                    // tell the player what their partner did
                    s1.partnerMoved(k, s2play);
                    s2.partnerMoved(j, s1play);
                }
            }
        }

        // sort results
        _pairs.sort(Comparator.comparing(ResultsPairing::getScore));
        Collections.reverse(_pairs);

        // tell listener we are DONE
        if (listener != null) {
            listener.OnTournamentDone( this );
        } else throw new RuntimeException("Tournament done, but has no listener to get back to");
    }

    private void outcome(int player1index, boolean move1, int player2index, boolean move2) {
        final int allmine = 60;
        final int together = 40;
        final int tugowar = 20;
        final int lost = 10;

        long player1points = 0;
        long player2points = 0;

        // cooperate-cooperate: 40, 40
        if (move1 && move2) {
            player1points = together;
            player2points = together;
        } else

            // defect-cooperate: 60, 10
            if (!move1 && move2) {
                player1points = allmine;
                player2points = lost;
            } else

                // cooperate-defect: 10, 60
                if (move1 && !move2) {
                    player1points = lost;
                    player2points = allmine;
                } else

                    // defect-defect: 20, 20
                    if (!move1 && !move2) {
                        player1points = tugowar;
                        player2points = tugowar;
                    }

        _pairs.get(player1index).score = _pairs.get(player1index).score + player1points;
        _pairs.get(player2index).score = _pairs.get(player2index).score + player2points;
        Log.d("Tournament", "Player A" + player1index + ": " + player1points + "p");
        Log.d("Tournament", "Player B" + player2index + ": " + player2points + "p");
    }

    public ArrayList<ResultsPairing> get_pairs() {
        return _pairs;
    }
}
