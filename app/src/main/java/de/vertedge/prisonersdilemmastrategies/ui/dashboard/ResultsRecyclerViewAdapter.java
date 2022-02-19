package de.vertedge.prisonersdilemmastrategies.ui.dashboard;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.vertedge.prisonersdilemmastrategies.R;
import de.vertedge.prisonersdilemmastrategies.model.Strategy;
import de.vertedge.prisonersdilemmastrategies.model.Tournament;
import de.vertedge.prisonersdilemmastrategies.ui.home.SelectStrategiesRecyclerViewAdapter;

public class ResultsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Tournament.ResultsPairing> results;
    private long max;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_row_results,parent,false);
        return new ResultsRecyclerViewAdapter.ResultsViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Tournament.ResultsPairing pairing = results.get(position);
        ResultsViewHolder viewHolder = (ResultsViewHolder) holder;

        viewHolder.txtView_title.setText( pairing.strategy.get_nameRID() );

        double percent = ((pairing.score + 0.00001) / max) * 100;
        viewHolder.progressBarScore.setMax( 100 );
        viewHolder.progressBarScore.setProgress( (int)Math.round( percent ) );



        viewHolder.txtView_description.setText(String.format(Locale.getDefault(), "%d / %d (%.2f%%)",
                pairing.score,
                max,
                percent));

        if (pairing.strategy.get_iconRID() != 0)
            viewHolder.imgView_icon.setImageResource( pairing.strategy.get_iconRID() );
    }

    public void updateList(final List<Tournament.ResultsPairing> resultsPairings) {
        if (results != null) this.results.clear();
        this.results = (ArrayList<Tournament.ResultsPairing>) resultsPairings;
        if ( (results != null) && (results.size() > 0) ){
            max = results.get(0).score;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (results == null) return 0;
        return results.size();
    }

    class ResultsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView_icon;
        TextView txtView_title;
        TextView txtView_description;
        ProgressBar progressBarScore;

        public ResultsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView_icon = itemView.findViewById(R.id.imgView_icon);
            txtView_title = itemView.findViewById(R.id.txtView_title);
            txtView_description = itemView.findViewById(R.id.txtView_description);
            progressBarScore = itemView.findViewById(R.id.progressBar);
        }
    }
}
