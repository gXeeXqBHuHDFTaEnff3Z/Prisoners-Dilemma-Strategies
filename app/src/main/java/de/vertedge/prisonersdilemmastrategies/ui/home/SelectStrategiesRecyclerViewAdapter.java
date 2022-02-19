package de.vertedge.prisonersdilemmastrategies.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.vertedge.prisonersdilemmastrategies.R;
import de.vertedge.prisonersdilemmastrategies.model.Strategy;

public class SelectStrategiesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface StrategiesSelectListener{
        public void onSelect(ArrayList<Strategy> strategyArrayList);
    }

    ArrayList<Strategy> strategyArrayList;
    ArrayList<Strategy> selectedStrategies;
    StrategiesSelectListener listener;

    public SelectStrategiesRecyclerViewAdapter(StrategiesSelectListener listener) {
        this.strategyArrayList = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_row_select_strategies,parent,false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Strategy strategy = strategyArrayList.get(position);
        RecyclerViewViewHolder viewHolder = (RecyclerViewViewHolder) holder;

        viewHolder.txtView_title.setText( strategy.get_nameRID() );
        viewHolder.txtView_description.setText( strategy.get_explanationRID() );

        if (strategy.get_iconRID() != 0)
        viewHolder.imgView_icon.setImageResource( strategy.get_iconRID() );

        viewHolder.checkBoxSelect.setTag( strategyArrayList.get(position) );
        viewHolder.checkBoxSelect.setOnClickListener(view -> {
            if (selectedStrategies == null){
                selectedStrategies = new ArrayList<>();
            }
            CheckBox cb = (CheckBox) view;
            if (!cb.isSelected()){
                selectedStrategies.add( strategyArrayList.get(position) );
                cb.setSelected( true );
            } else {
                selectedStrategies.remove( strategyArrayList.get(position) );
                cb.setSelected( false );
            }
            if (listener != null){
                listener.onSelect(selectedStrategies);
            }
        });

    }

    @Override
    public int getItemCount() {
        return strategyArrayList.size();
    }

    public void updateList(final List<Strategy> strategyList) {
        this.strategyArrayList.clear();
        this.strategyArrayList = (ArrayList<Strategy>) strategyList;
        notifyDataSetChanged();
    }

    public ArrayList<Strategy> getSelectedStrategies() {
        return selectedStrategies;
    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView_icon;
        TextView txtView_title;
        TextView txtView_description;
        CheckBox checkBoxSelect;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView_icon = itemView.findViewById(R.id.imgView_icon);
            txtView_title = itemView.findViewById(R.id.txtView_title);
            txtView_description = itemView.findViewById(R.id.txtView_description);
            checkBoxSelect = itemView.findViewById(R.id.checkBox);
        }
    }
}