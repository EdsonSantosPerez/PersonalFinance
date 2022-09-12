package com.edson.personalfinance.ui.depense;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.edson.personalfinance.R;
import com.edson.personalfinance.sqlite.database.model.Depense;

public class DepenseHolder extends RecyclerView.ViewHolder {

    private TextView txtRowDate;
    private TextView txtRowCategory;
    private TextView txtRowDescription;
    private TextView txtRowValue;

    public DepenseHolder(View itemView) {
        super(itemView);
        txtRowDate = itemView.findViewById(R.id.txtRowDate);
        txtRowCategory = itemView.findViewById(R.id.txtRowCategory);
        txtRowDescription = itemView.findViewById(R.id.txtRowDescription);
        txtRowValue = itemView.findViewById(R.id.txtRowValue);
    }

    public void setDetails(Depense depense) {
        txtRowDate.setText(depense.getDateTime());
        txtRowCategory.setText(depense.getCategory());
        txtRowDescription.setText(depense.getDescription());
        txtRowValue.setText(depense.getValue() + " €");
    }

}
