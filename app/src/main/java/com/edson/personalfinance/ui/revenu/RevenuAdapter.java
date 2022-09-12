package com.edson.personalfinance.ui.revenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edson.personalfinance.R;
import com.edson.personalfinance.sqlite.database.model.Revenu;

import java.util.List;

public class RevenuAdapter extends RecyclerView.Adapter<RevenuHolder> {

    private Context context;
    private List<Revenu> revenus;

    public RevenuAdapter(Context context, List<Revenu> revenus) {
        this.context = context;
        this.revenus = revenus;
    }

    @NonNull
    @Override
    public RevenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        return new RevenuHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RevenuHolder holder, int position) {
        Revenu revenu = revenus.get(position);
        holder.setDetails(revenu);
    }

    @Override
    public int getItemCount() {
        return revenus.size();
    }
}
