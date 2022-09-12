package com.edson.personalfinance.ui.depense;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edson.personalfinance.R;
import com.edson.personalfinance.sqlite.database.model.Depense;

import java.util.List;

public class DepenseAdapter extends RecyclerView.Adapter<DepenseHolder> {

    private Context context;
    private List<Depense> depenses;

    public DepenseAdapter(Context context, List<Depense> depenses) {
        this.context = context;
        this.depenses = depenses;
    }

    @NonNull
    @Override
    public DepenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
            return new DepenseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepenseHolder holder, int position) {
        Depense depense = depenses.get(position);
        holder.setDetails(depense);
    }

    @Override
    public int getItemCount() {
        return depenses.size();
    }
}
